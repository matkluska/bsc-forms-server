package io.kluska.bsc.forms.reply.stats.service.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Mateusz Kluska
 */
@Data
@Builder
public class FormStats {
    private List<QuestionStats> questionStats;
}
