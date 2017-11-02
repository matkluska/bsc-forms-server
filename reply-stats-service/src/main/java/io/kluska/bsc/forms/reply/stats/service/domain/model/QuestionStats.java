package io.kluska.bsc.forms.reply.stats.service.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Mateusz Kluska
 */
@Data
@NoArgsConstructor
public abstract class QuestionStats {
    private long repliesCount;
}
