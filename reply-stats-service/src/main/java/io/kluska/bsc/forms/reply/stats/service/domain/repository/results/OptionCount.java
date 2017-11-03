package io.kluska.bsc.forms.reply.stats.service.domain.repository.results;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Mateusz Kluska
 */
@Data
@NoArgsConstructor
public class OptionCount {
    private String option;
    private long count;
}
