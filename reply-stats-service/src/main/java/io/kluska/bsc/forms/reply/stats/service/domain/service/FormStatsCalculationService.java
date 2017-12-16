package io.kluska.bsc.forms.reply.stats.service.domain.service;

import io.kluska.bsc.forms.form.api.dto.FormDTO;
import io.kluska.bsc.forms.form.api.dto.QuestionDTO;
import io.kluska.bsc.forms.form.api.dto.question.LinearScaleDTO;
import io.kluska.bsc.forms.form.api.dto.question.LongTextDTO;
import io.kluska.bsc.forms.form.api.dto.question.MultipleChoiceDTO;
import io.kluska.bsc.forms.form.api.dto.question.ShortTextDTO;
import io.kluska.bsc.forms.form.api.dto.question.SingleChoiceDTO;
import io.kluska.bsc.forms.reply.stats.service.domain.model.FormStats;
import io.kluska.bsc.forms.reply.stats.service.domain.model.QuestionStats;
import io.kluska.bsc.forms.reply.stats.service.domain.model.stats.LinearScaleStats;
import io.kluska.bsc.forms.reply.stats.service.domain.model.stats.LongTextStats;
import io.kluska.bsc.forms.reply.stats.service.domain.model.stats.MultipleChoiceStats;
import io.kluska.bsc.forms.reply.stats.service.domain.model.stats.ShortTextStats;
import io.kluska.bsc.forms.reply.stats.service.domain.model.stats.SingleChoiceStats;
import io.kluska.bsc.forms.reply.stats.service.domain.repository.FormStatsFactorsRepository;
import io.kluska.bsc.forms.reply.stats.service.domain.repository.FormStatsRepository;
import io.kluska.bsc.forms.reply.stats.service.domain.repository.results.OptionStats;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

/**
 * @author Mateusz Kluska
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FormStatsCalculationService {
    @NonNull
    private final FormStatsFactorsRepository statsFactorsRepository;
    @NonNull
    private final FormStatsRepository formStatsRepository;

    @Async
    void calcAndSaveFormStats(@NonNull final String formId, @NonNull final FormDTO formDTO) {
        List<QuestionStats> questionStats = formDTO.getQuestions().stream()
                .map(q -> getQuestionStats(q, formId))
                .collect(Collectors.toList());

        FormStats formStats = FormStats.builder()
                .formId(formId)
                .questionStats(questionStats)
                .build();

        formStatsRepository.save(formStats);
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

        long repliesCount = statsFactorsRepository.getRepliesCount(formId, shortTextDTO.getId());
        Map<String, Long> optionToCount = statsFactorsRepository.getOptionToRepliesCountMap(shortTextDTO.getId(), formId);

        shortTextStats.setQuestionId(shortTextDTO.getId());
        shortTextStats.setRepliesCount(repliesCount);
        shortTextStats.setRepliesToCount(optionToCount);

        return shortTextStats;
    }

    private LongTextStats getLongTextStats(@NonNull final LongTextDTO longTextDTO, @NonNull final String formId) {
        LongTextStats longTextStats = new LongTextStats();

        long repliesCount = statsFactorsRepository.getRepliesCount(formId, longTextDTO.getId());
        Map<String, Long> optionToCount = statsFactorsRepository.getOptionToRepliesCountMap(longTextDTO.getId(), formId);

        longTextStats.setQuestionId(longTextDTO.getId());
        longTextStats.setRepliesCount(repliesCount);
        longTextStats.setRepliesToCount(optionToCount);
        return longTextStats;
    }

    private SingleChoiceStats getSingleChoiceStats(@NonNull final SingleChoiceDTO singleChoiceDTO, @NonNull final String formId) {
        SingleChoiceStats singleChoiceStats = new SingleChoiceStats();

        Map<String, Long> optionIdToCount = statsFactorsRepository.getOptionIdToRepliesCountMap(singleChoiceDTO, formId);
        long repliesCount = statsFactorsRepository.getRepliesCount(formId, singleChoiceDTO.getId());

        singleChoiceStats.setQuestionId(singleChoiceDTO.getId());
        singleChoiceStats.setRepliesCount(repliesCount);
        singleChoiceStats.setOptionIdToRepliesCounts(optionIdToCount);
        return singleChoiceStats;
    }

    private LinearScaleStats getLinearScaleStats(@NonNull final LinearScaleDTO linearScaleDTO, @NonNull final String formId) {
        LinearScaleStats linearScaleStats = new LinearScaleStats();

        Optional<OptionStats> optionsStats = statsFactorsRepository.getOptionsStats(formId, linearScaleDTO);
        Map<String, Long> optionToCount = statsFactorsRepository.getOptionToRepliesCountMap(linearScaleDTO.getId(), formId);

        linearScaleStats.setQuestionId(linearScaleDTO.getId());
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

        long repliesCount = statsFactorsRepository.getRepliesCount(formId, multipleChoiceDTO.getId());
        Map<String, Long> optionIdToCount = statsFactorsRepository.getOptionIdToRepliesCountMap(multipleChoiceDTO, formId);

        multipleChoiceStats.setQuestionId(multipleChoiceDTO.getId());
        multipleChoiceStats.setRepliesCount(repliesCount);
        multipleChoiceStats.setOptionIdToRepliesCounts(optionIdToCount);
        return multipleChoiceStats;
    }
}
