package io.kluska.bsc.forms.reply.stats.service.domain.repository.results;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Mateusz Kluska
 */
@Data
@NoArgsConstructor
public class OptionIdCount {
    private String optionId;
    private long count;
}
