package io.kluska.bsc.forms.reply.stats.service.domain.service;

import io.kluska.bsc.forms.form.api.dto.FormDTO;
import io.kluska.bsc.forms.reply.stats.service.api.client.FormClient;
import io.kluska.bsc.forms.reply.stats.service.domain.model.FormStats;
import io.kluska.bsc.forms.reply.stats.service.domain.repository.StatsRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Mateusz Kluska
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StatsService {
    @NonNull
    private final StatsRepository statsRepository;
    @NonNull
    private final FormClient formClient;

    public FormStats getFormStats(String formId) {
        FormDTO formDTO = formClient.findFormById(formId);
        return statsRepository.getFormStats(formId, formDTO);
    }
}
