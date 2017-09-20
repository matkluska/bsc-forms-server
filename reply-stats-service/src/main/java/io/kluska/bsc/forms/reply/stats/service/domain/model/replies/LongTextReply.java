package io.kluska.bsc.forms.reply.stats.service.domain.model.replies;

import io.kluska.bsc.forms.form.api.dto.QuestionDTO;
import io.kluska.bsc.forms.reply.stats.service.domain.model.Reply;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Mateusz Kluska
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LongTextReply extends Reply {
    private String option;

    @Override
    public boolean validate(QuestionDTO questionDTO) {
        return true;
    }
}
