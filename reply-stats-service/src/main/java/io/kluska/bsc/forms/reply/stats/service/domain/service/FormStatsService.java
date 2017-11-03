package io.kluska.bsc.forms.reply.stats.service.domain.service;

import io.kluska.bsc.forms.reply.stats.service.api.exception.FormStatsNotFoundException;
import io.kluska.bsc.forms.reply.stats.service.domain.model.FormStats;
import io.kluska.bsc.forms.reply.stats.service.domain.repository.FormStatsRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Mateusz Kluska
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FormStatsService {
    @NonNull
    private final FormStatsRepository formStatsRepository;

    public FormStats getFormStats(@NonNull final String formId) {
        return formStatsRepository.findOneByFormId(formId).orElseThrow(FormStatsNotFoundException::new);
    }
}
