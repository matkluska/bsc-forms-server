package io.kluska.bsc.forms.reply.stats.service.api.dto.stats;

import io.kluska.bsc.forms.reply.stats.service.api.dto.QuestionStatsDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * @author Mateusz Kluska
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MultipleChoiceStatsDTO extends QuestionStatsDTO {
    private Map<String, Long> optionIdToRepliesCounts;
}
