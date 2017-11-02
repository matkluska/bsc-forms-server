package io.kluska.bsc.forms.reply.stats.service.domain.repository;

import io.kluska.bsc.forms.form.api.dto.FormDTO;
import io.kluska.bsc.forms.reply.stats.service.domain.model.FormStats;

/**
 * @author Mateusz Kluska
 */
public interface StatsRepository {
    FormStats getFormStats(String formId, FormDTO formDTO);
}
