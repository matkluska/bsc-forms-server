package io.kluska.bsc.forms.reply.stats.service.domain.repository;

import io.kluska.bsc.forms.form.api.dto.FormDTO;
import io.kluska.bsc.forms.form.api.dto.QuestionDTO;
import io.kluska.bsc.forms.form.api.dto.question.LongTextDTO;
import io.kluska.bsc.forms.form.api.dto.question.ShortTextDTO;
import io.kluska.bsc.forms.form.api.dto.question.SingleChoiceDTO;
import io.kluska.bsc.forms.reply.stats.service.domain.model.FormStats;
import io.kluska.bsc.forms.reply.stats.service.domain.model.QuestionStats;
import io.kluska.bsc.forms.reply.stats.service.domain.model.Reply;
import io.kluska.bsc.forms.reply.stats.service.domain.model.replies.SingleChoiceReply;
import io.kluska.bsc.forms.reply.stats.service.domain.model.stats.LongTextStats;
import io.kluska.bsc.forms.reply.stats.service.domain.model.stats.ShortTextStats;
import io.kluska.bsc.forms.reply.stats.service.domain.model.stats.SingleChoiceStats;
import io.kluska.bsc.forms.reply.stats.service.domain.repository.results.OptionIdCount;
import io.kluska.bsc.forms.reply.stats.service.domain.repository.results.RepliesCount;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
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
                .filter(questionDTO -> questionDTO instanceof ShortTextDTO || questionDTO instanceof SingleChoiceDTO
                        || questionDTO instanceof LongTextDTO)
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
                //                Case($(instanceOf(MultipleChoiceDTO.class)), () -> modelMapper.map(questionDTO, MultipleChoice.class)),
                //                Case($(instanceOf(LinearScaleDTO.class)), () -> modelMapper.map(questionDTO, LinearScale.class)),
                Case($(), () -> {
                    throw new RuntimeException("Unsupported question type");
                }));
    }

    private ShortTextStats getShortTextStats(@NonNull final ShortTextDTO shortTextDTO, @NonNull final String formId) {
        ShortTextStats shortTextStats = new ShortTextStats();

        long repliesCount = getRepliesCount(shortTextDTO.getId(), formId);
        List<String> distinctReplies = getDistinctReplies(shortTextDTO.getId(), formId);

        shortTextStats.setRepliesCount(repliesCount);
        shortTextStats.setReplies(distinctReplies);

        return shortTextStats;
    }

    private LongTextStats getLongTextStats(@NonNull final LongTextDTO longTextDTO, @NonNull final String formId) {
        LongTextStats longTextStats = new LongTextStats();

        long repliesCount = getRepliesCount(longTextDTO.getId(), formId);
        List<String> distinctReplies = getDistinctReplies(longTextDTO.getId(), formId);

        longTextStats.setRepliesCount(repliesCount);
        longTextStats.setReplies(distinctReplies);
        return longTextStats;
    }

    private SingleChoiceStats getSingleChoiceStats(@NonNull final SingleChoiceDTO singleChoiceDTO, @NonNull final String formId) {
        SingleChoiceStats singleChoiceStats = new SingleChoiceStats();

        Map<String, Long> optionIdToCount = getOptionIdToRepliesCountMap(singleChoiceDTO, formId);

        singleChoiceStats.setRepliesCount(getRepliesCount(formId, singleChoiceDTO.getId()));
        singleChoiceStats.setOptionIdToRepliesCounts(optionIdToCount);
        return singleChoiceStats;
    }

    private long getRepliesCount(@NonNull final String formId, @NonNull final String questionId) {
        Aggregation agg = newAggregation(match(new Criteria("formId").is(formId)),
                match(new Criteria("questionId").is(questionId)),
                count().as("value"));
        AggregationResults<RepliesCount> aggResult = mongoTemplate.aggregate(agg, Reply.class, RepliesCount.class);
        return aggResult.getUniqueMappedResult().getValue();
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

    @SuppressWarnings("unchecked")
    private List<String> getDistinctReplies(@NonNull final String questionId, @NonNull final String formId) {
        Query query = new Query();
        query.addCriteria(new Criteria("formId").is(formId));
        query.addCriteria(new Criteria("questionId").is(questionId));

        return mongoTemplate.getCollection(Reply.REPLIES_COLLECTION).distinct("option", query.getQueryObject());
    }
}
