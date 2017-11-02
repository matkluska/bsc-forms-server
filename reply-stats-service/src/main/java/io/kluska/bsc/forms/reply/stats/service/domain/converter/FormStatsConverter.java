package io.kluska.bsc.forms.reply.stats.service.domain.converter;

import io.kluska.bsc.forms.reply.stats.service.api.dto.FormStatsDTO;
import io.kluska.bsc.forms.reply.stats.service.api.dto.QuestionStatsDTO;
import io.kluska.bsc.forms.reply.stats.service.domain.model.FormStats;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Mateusz Kluska
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FormStatsConverter implements Function<FormStats, FormStatsDTO> {
    private final QuestionStatsConverter questionStatsConverter;

    @Override
    public FormStatsDTO apply(FormStats formStats) {
        List<QuestionStatsDTO> questionStats = Optional.ofNullable(formStats.getQuestionStats())
                .map(questions -> questions.stream()
                        .map(questionStatsConverter)
                        .collect(Collectors.toList()))
                .orElse(new ArrayList<>());

        FormStatsDTO formStatsDTO = new FormStatsDTO();
        formStatsDTO.setQuestionStats(questionStats);
        return formStatsDTO;

    }
}
