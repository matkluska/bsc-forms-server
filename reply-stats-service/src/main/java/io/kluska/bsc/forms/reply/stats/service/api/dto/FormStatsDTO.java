package io.kluska.bsc.forms.reply.stats.service.api.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Mateusz Kluska
 */
@Data
public class FormStatsDTO {
    private List<QuestionStatsDTO> questionStats;
}
