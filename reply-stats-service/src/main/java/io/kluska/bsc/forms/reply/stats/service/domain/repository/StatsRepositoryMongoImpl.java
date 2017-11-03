package io.kluska.bsc.forms.reply.stats.service.domain.repository;

import io.kluska.bsc.forms.form.api.dto.FormDTO;
import io.kluska.bsc.forms.form.api.dto.QuestionDTO;
import io.kluska.bsc.forms.form.api.dto.question.LinearScaleDTO;
import io.kluska.bsc.forms.form.api.dto.question.LongTextDTO;
import io.kluska.bsc.forms.form.api.dto.question.MultipleChoiceDTO;
import io.kluska.bsc.forms.form.api.dto.question.ShortTextDTO;
import io.kluska.bsc.forms.form.api.dto.question.SingleChoiceDTO;
import io.kluska.bsc.forms.reply.stats.service.domain.model.FormStats;
import io.kluska.bsc.forms.reply.stats.service.domain.model.QuestionStats;
import io.kluska.bsc.forms.reply.stats.service.domain.model.Reply;
import io.kluska.bsc.forms.reply.stats.service.domain.model.replies.LinearScaleReply;
import io.kluska.bsc.forms.reply.stats.service.domain.model.replies.MultipleChoiceReply;
import io.kluska.bsc.forms.reply.stats.service.domain.model.replies.SingleChoiceReply;
import io.kluska.bsc.forms.reply.stats.service.domain.model.stats.LinearScaleStats;
import io.kluska.bsc.forms.reply.stats.service.domain.model.stats.LongTextStats;
import io.kluska.bsc.forms.reply.stats.service.domain.model.stats.MultipleChoiceStats;
import io.kluska.bsc.forms.reply.stats.service.domain.model.stats.ShortTextStats;
import io.kluska.bsc.forms.reply.stats.service.domain.model.stats.SingleChoiceStats;
import io.kluska.bsc.forms.reply.stats.service.domain.repository.results.OptionCount;
import io.kluska.bsc.forms.reply.stats.service.domain.repository.results.OptionIdCount;
import io.kluska.bsc.forms.reply.stats.service.domain.repository.results.OptionIdsCount;
import io.kluska.bsc.forms.reply.stats.service.domain.repository.results.OptionStats;
import io.kluska.bsc.forms.reply.stats.service.domain.repository.results.RepliesCount;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.count;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;

/**
 * @author Mateusz Kluska
 */
@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StatsRepositoryMongoImpl implements StatsRepository {
    @NonNull
    private final MongoTemplate mongoTemplate;

    @Override
    public FormStats getFormStats(@NonNull final String formId, @NonNull final FormDTO formDTO) {
        List<QuestionStats> questionStats = formDTO.getQuestions().stream()
                .map(q -> getQuestionStats(q, formId))
                .collect(Collectors.toList());
        return FormStats.builder()
                .questionStats(questionStats)
                .build();
    }

    private QuestionStats getQuestionStats(QuestionDTO questionDTO, String formId) {
        return Match(questionDTO).of(
                Case($(instanceOf(ShortTextDTO.class)), () -> getShortTextStats((ShortTextDTO) questionDTO, formId)),
                Case($(instanceOf(LongTextDTO.class)), () -> getLongTextStats((LongTextDTO) questionDTO, formId)),
                Case($(instanceOf(SingleChoiceDTO.class)), () -> getSingleChoiceStats((SingleChoiceDTO) questionDTO, formId)),
                Case($(instanceOf(MultipleChoiceDTO.class)), () -> getMultipleChoiceStats((MultipleChoiceDTO) questionDTO, formId)),
                Case($(instanceOf(LinearScaleDTO.class)), () -> getLinearScaleStats((LinearScaleDTO) questionDTO, formId)),
                Case($(), () -> {
                    throw new RuntimeException("Unsupported question type");
                }));
    }

    private ShortTextStats getShortTextStats(@NonNull final ShortTextDTO shortTextDTO, @NonNull final String formId) {
        ShortTextStats shortTextStats = new ShortTextStats();

        long repliesCount = getRepliesCount(formId, shortTextDTO.getId());
        Map<String, Long> optionToCount = getOptionToRepliesCountMap(shortTextDTO.getId(), formId);

        shortTextStats.setRepliesCount(repliesCount);
        shortTextStats.setRepliesToCount(optionToCount);

        return shortTextStats;
    }

    private LongTextStats getLongTextStats(@NonNull final LongTextDTO longTextDTO, @NonNull final String formId) {
        LongTextStats longTextStats = new LongTextStats();

        long repliesCount = getRepliesCount(formId, longTextDTO.getId());
        Map<String, Long> optionToCount = getOptionToRepliesCountMap(longTextDTO.getId(), formId);

        longTextStats.setRepliesCount(repliesCount);
        longTextStats.setRepliesToCount(optionToCount);
        return longTextStats;
    }

    private SingleChoiceStats getSingleChoiceStats(@NonNull final SingleChoiceDTO singleChoiceDTO, @NonNull final String formId) {
        SingleChoiceStats singleChoiceStats = new SingleChoiceStats();

        Map<String, Long> optionIdToCount = getOptionIdToRepliesCountMap(singleChoiceDTO, formId);

        singleChoiceStats.setRepliesCount(getRepliesCount(formId, singleChoiceDTO.getId()));
        singleChoiceStats.setOptionIdToRepliesCounts(optionIdToCount);
        return singleChoiceStats;
    }

    private LinearScaleStats getLinearScaleStats(@NonNull final LinearScaleDTO linearScaleDTO, @NonNull final String formId) {
        LinearScaleStats linearScaleStats = new LinearScaleStats();

//        long repliesCount = getRepliesCount(formId, linearScaleDTO.getId());
        Optional<OptionStats> optionsStats = getOptionsStats(formId, linearScaleDTO);
        Map<String, Long> optionToCount = getOptionToRepliesCountMap(linearScaleDTO.getId(), formId);

        linearScaleStats.setRepliesCount(optionsStats
                .map(OptionStats::getCount)
                .orElse(0L));
        linearScaleStats.setAvgValue(optionsStats
                .map(OptionStats::getAvg)
                .orElse(0.0F));
        linearScaleStats.setOptionToRepliesCounts(optionToCount);
        return linearScaleStats;
    }

    private MultipleChoiceStats getMultipleChoiceStats(@NonNull final MultipleChoiceDTO multipleChoiceDTO, @NonNull final String formId) {
        MultipleChoiceStats multipleChoiceStats = new MultipleChoiceStats();

        long repliesCount = getRepliesCount(formId, multipleChoiceDTO.getId());
        Map<String, Long> optionIdToCount = getOptionIdToRepliesCountMap(multipleChoiceDTO, formId);

        multipleChoiceStats.setRepliesCount(repliesCount);
        multipleChoiceStats.setOptionIdToRepliesCounts(optionIdToCount);
        return multipleChoiceStats;
    }

    private long getRepliesCount(@NonNull final String formId, @NonNull final String questionId) {
        Aggregation agg = newAggregation(match(new Criteria("formId").is(formId)),
                match(new Criteria("questionId").is(questionId)),
                count().as("value"));
        AggregationResults<RepliesCount> aggResult = mongoTemplate.aggregate(agg, Reply.class, RepliesCount.class);
        return Optional.ofNullable(aggResult.getUniqueMappedResult())
                .map(RepliesCount::getValue)
                .orElse(0L);
    }

    private Map<String, Long> getOptionIdToRepliesCountMap(@NonNull final SingleChoiceDTO singleChoiceDTO, @NonNull final String formId) {
        Aggregation agg = newAggregation(match(new Criteria("formId").is(formId)),
                match(new Criteria("questionId").is(singleChoiceDTO.getId())),
                group("optionId").count().as("count"),
                project("count").and("optionId").previousOperation());
        AggregationResults<OptionIdCount> aggResult = mongoTemplate.aggregate(agg, SingleChoiceReply.class, OptionIdCount.class);
        return aggResult.getMappedResults().stream()
                .collect(Collectors.toMap(OptionIdCount::getOptionId, OptionIdCount::getCount));
    }

    private Map<String, Long> getOptionIdToRepliesCountMap(@NonNull final MultipleChoiceDTO multipleChoiceDTO, @NonNull final String formId) {
        Aggregation agg = newAggregation(match(new Criteria("formId").is(formId)),
                match(new Criteria("questionId").is(multipleChoiceDTO.getId())),
                unwind("optionIds", false),
                group("optionIds").count().as("count"),
                project("count").and("optionIds").previousOperation());
        AggregationResults<OptionIdsCount> aggResult = mongoTemplate.aggregate(agg, MultipleChoiceReply.class, OptionIdsCount.class);
        return aggResult.getMappedResults().stream()
                .collect(Collectors.toMap(OptionIdsCount::getOptionIds, OptionIdsCount::getCount));
    }

    private Map<String, Long> getOptionToRepliesCountMap(@NonNull final String questionId, @NonNull final String formId) {
        Aggregation agg = newAggregation(match(new Criteria("formId").is(formId)),
                match(new Criteria("questionId").is(questionId)),
                group("option").count().as("count"),
                project("count").and("option").previousOperation());
        AggregationResults<OptionCount> aggResult = mongoTemplate.aggregate(agg, LinearScaleReply.class, OptionCount.class);
        return aggResult.getMappedResults().stream()
                .collect(Collectors.toMap(OptionCount::getOption, OptionCount::getCount));
    }

    private Optional<OptionStats> getOptionsStats(@NonNull final String formId, @NonNull final LinearScaleDTO linearScaleDTO) {
        Aggregation agg = newAggregation(match(new Criteria("formId").is(formId)),
                match(new Criteria("questionId").is(linearScaleDTO.getId())),
                group("questionId")
                        .avg("option").as("avg")
                        .count().as("count"));
        AggregationResults<OptionStats> aggResult = mongoTemplate.aggregate(agg, LinearScaleReply.class, OptionStats.class);
        return Optional.ofNullable(aggResult.getUniqueMappedResult());
    }
}
