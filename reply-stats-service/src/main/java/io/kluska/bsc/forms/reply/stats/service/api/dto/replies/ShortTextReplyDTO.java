package io.kluska.bsc.forms.reply.stats.service.api.dto.replies;

import io.kluska.bsc.forms.reply.stats.service.api.dto.ReplyDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

/**
 * @author Mateusz Kluska
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ShortTextReplyDTO extends ReplyDTO {
    @NotNull
    @NonNull
    private String option;
}
