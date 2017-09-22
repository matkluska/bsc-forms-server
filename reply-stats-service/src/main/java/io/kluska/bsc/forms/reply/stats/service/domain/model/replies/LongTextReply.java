package io.kluska.bsc.forms.reply.stats.service.domain.model.replies;

import io.kluska.bsc.forms.form.api.dto.QuestionDTO;
import io.kluska.bsc.forms.form.api.dto.question.LongTextDTO;
import io.kluska.bsc.forms.reply.stats.service.api.exception.BadReplyTypeException;
import io.kluska.bsc.forms.reply.stats.service.domain.model.Reply;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author Mateusz Kluska
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LongTextReply extends Reply {
    @NonNull
    private String option;

    @Override
    public void validate(QuestionDTO questionDTO) {
        if (!(questionDTO instanceof LongTextDTO))
            throw new BadReplyTypeException("Reply for question " + getQuestionId() +
                    " should have type " + LongTextDTO.class.getSimpleName());
    }
}
