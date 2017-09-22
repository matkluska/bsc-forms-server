package io.kluska.bsc.forms.reply.stats.service.domain.model.replies;

import io.kluska.bsc.forms.form.api.dto.QuestionDTO;
import io.kluska.bsc.forms.form.api.dto.question.LinearScaleDTO;
import io.kluska.bsc.forms.reply.stats.service.api.exception.BadReplyTypeException;
import io.kluska.bsc.forms.reply.stats.service.api.exception.NotDefinedOptionException;
import io.kluska.bsc.forms.reply.stats.service.domain.model.Reply;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Mateusz Kluska
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LinearScaleReply extends Reply {
    private int option;

    @Override
    public void validate(QuestionDTO questionDTO) {
        if (!(questionDTO instanceof LinearScaleDTO))
            throw new BadReplyTypeException("Reply for question " + getQuestionId() +
                    " should have type " + LinearScaleDTO.class.getSimpleName());

        validateOption((LinearScaleDTO) questionDTO);
    }

    private void validateOption(LinearScaleDTO linearScaleQuestion) {
        if (option > linearScaleQuestion.getMax() || option < linearScaleQuestion.getMin())
            throw new NotDefinedOptionException(option + " is out of the range.");
    }
}
