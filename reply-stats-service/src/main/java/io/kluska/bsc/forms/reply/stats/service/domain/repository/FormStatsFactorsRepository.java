package io.kluska.bsc.forms.reply.stats.service.domain.repository;

import io.kluska.bsc.forms.form.api.dto.question.LinearScaleDTO;
import io.kluska.bsc.forms.form.api.dto.question.MultipleChoiceDTO;
import io.kluska.bsc.forms.form.api.dto.question.SingleChoiceDTO;
import io.kluska.bsc.forms.reply.stats.service.domain.repository.results.OptionStats;
import lombok.NonNull;

import java.util.Map;
import java.util.Optional;

/**
 * @author Mateusz Kluska
 */
public interface FormStatsFactorsRepository {

    long getRepliesCount(@NonNull final String formId, @NonNull final String questionId);

    Map<String, Long> getOptionIdToRepliesCountMap(@NonNull final SingleChoiceDTO singleChoiceDTO, @NonNull final String formId);

    Map<String, Long> getOptionIdToRepliesCountMap(@NonNull final MultipleChoiceDTO multipleChoiceDTO, @NonNull final String formId);

    Map<String, Long> getOptionToRepliesCountMap(@NonNull final String questionId, @NonNull final String formId);

    Optional<OptionStats> getOptionsStats(@NonNull final String formId, @NonNull final LinearScaleDTO linearScaleDTO);
}
