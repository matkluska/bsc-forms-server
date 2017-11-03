package io.kluska.bsc.forms.reply.stats.service.domain.model.stats;

import io.kluska.bsc.forms.reply.stats.service.domain.model.QuestionStats;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * @author Mateusz Kluska
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LinearScaleStats extends QuestionStats {
    private float avgValue;
    private Map<String, Long> optionToRepliesCounts;
}
