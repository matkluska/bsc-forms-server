package io.kluska.bsc.forms.reply.stats.service.domain.model.stats;

import io.kluska.bsc.forms.reply.stats.service.domain.model.QuestionStats;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * @author Mateusz Kluska
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class MultipleChoiceStats extends QuestionStats {
    private Map<String, Long> optionIdToRepliesCounts;
}
