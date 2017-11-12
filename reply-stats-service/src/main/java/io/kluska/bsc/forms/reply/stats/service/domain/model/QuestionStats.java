package io.kluska.bsc.forms.reply.stats.service.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author Mateusz Kluska
 */
@Data
@NoArgsConstructor
public abstract class QuestionStats {
    @NonNull
    private String questionId;
    private long repliesCount;
}
