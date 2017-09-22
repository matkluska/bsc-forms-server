package io.kluska.bsc.forms.reply.stats.service.api.dto.replies;

import cz.jirutka.validator.collection.constraints.EachNotNull;
import io.kluska.bsc.forms.reply.stats.service.api.dto.ReplyDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Mateusz Kluska
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MultipleChoiceReplyDTO extends ReplyDTO {
    @NonNull
    @NotNull
    @EachNotNull
    private List<String> optionIds;
}
