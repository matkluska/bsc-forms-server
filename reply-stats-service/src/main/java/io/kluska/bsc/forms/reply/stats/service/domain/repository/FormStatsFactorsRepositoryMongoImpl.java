package io.kluska.bsc.forms.reply.stats.service.domain.repository;

import io.kluska.bsc.forms.form.api.dto.question.LinearScaleDTO;
import io.kluska.bsc.forms.form.api.dto.question.MultipleChoiceDTO;
import io.kluska.bsc.forms.form.api.dto.question.SingleChoiceDTO;
import io.kluska.bsc.forms.reply.stats.service.domain.model.Reply;
import io.kluska.bsc.forms.reply.stats.service.domain.model.replies.LinearScaleReply;
import io.kluska.bsc.forms.reply.stats.service.domain.model.replies.MultipleChoiceReply;
import io.kluska.bsc.forms.reply.stats.service.domain.model.replies.SingleChoiceReply;
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

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
public class FormStatsFactorsRepositoryMongoImpl implements FormStatsFactorsRepository {
    @NonNull
    private final MongoTemplate mongoTemplate;

    @Override
    public long getRepliesCount(@NonNull final String formId, @NonNull final String questionId) {
        Aggregation agg = newAggregation(match(new Criteria("formId").is(formId)),
                match(new Criteria("questionId").is(questionId)),
                count().as("value"));
        AggregationResults<RepliesCount> aggResult = mongoTemplate.aggregate(agg, Reply.class, RepliesCount.class);
        return Optional.ofNullable(aggResult.getUniqueMappedResult())
                .map(RepliesCount::getValue)
                .orElse(0L);
    }

    @Override
    public Map<String, Long> getOptionIdToRepliesCountMap(@NonNull final SingleChoiceDTO singleChoiceDTO, @NonNull final String formId) {
        Aggregation agg = newAggregation(match(new Criteria("formId").is(formId)),
                match(new Criteria("questionId").is(singleChoiceDTO.getId())),
                group("optionId").count().as("count"),
                project("count").and("optionId").previousOperation());
        AggregationResults<OptionIdCount> aggResult = mongoTemplate.aggregate(agg, SingleChoiceReply.class, OptionIdCount.class);
        return aggResult.getMappedResults().stream()
                .collect(Collectors.toMap(OptionIdCount::getOptionId, OptionIdCount::getCount));
    }

    @Override
    public Map<String, Long> getOptionIdToRepliesCountMap(@NonNull final MultipleChoiceDTO multipleChoiceDTO, @NonNull final String formId) {
        Aggregation agg = newAggregation(match(new Criteria("formId").is(formId)),
                match(new Criteria("questionId").is(multipleChoiceDTO.getId())),
                unwind("optionIds", false),
                group("optionIds").count().as("count"),
                project("count").and("optionIds").previousOperation());
        AggregationResults<OptionIdsCount> aggResult = mongoTemplate.aggregate(agg, MultipleChoiceReply.class, OptionIdsCount.class);
        return aggResult.getMappedResults().stream()
                .collect(Collectors.toMap(OptionIdsCount::getOptionIds, OptionIdsCount::getCount));
    }

    @Override
    public Map<String, Long> getOptionToRepliesCountMap(@NonNull final String questionId, @NonNull final String formId) {
        Aggregation agg = newAggregation(match(new Criteria("formId").is(formId)),
                match(new Criteria("questionId").is(questionId)),
                group("option").count().as("count"),
                project("count").and("option").previousOperation());
        AggregationResults<OptionCount> aggResult = mongoTemplate.aggregate(agg, LinearScaleReply.class, OptionCount.class);
        return aggResult.getMappedResults().stream()
                .collect(Collectors.toMap(OptionCount::getOption, OptionCount::getCount));
    }

    @Override
    public Optional<OptionStats> getOptionsStats(@NonNull final String formId, @NonNull final LinearScaleDTO linearScaleDTO) {
        Aggregation agg = newAggregation(match(new Criteria("formId").is(formId)),
                match(new Criteria("questionId").is(linearScaleDTO.getId())),
                group("questionId")
                        .avg("option").as("avg")
                        .count().as("count"));
        AggregationResults<OptionStats> aggResult = mongoTemplate.aggregate(agg, LinearScaleReply.class, OptionStats.class);
        return Optional.ofNullable(aggResult.getUniqueMappedResult());
    }
}
