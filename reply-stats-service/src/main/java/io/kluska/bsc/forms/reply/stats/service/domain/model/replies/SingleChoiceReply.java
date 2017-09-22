package io.kluska.bsc.forms.reply.stats.service.domain.model.replies;

import io.kluska.bsc.forms.form.api.dto.OptionDTO;
import io.kluska.bsc.forms.form.api.dto.QuestionDTO;
import io.kluska.bsc.forms.form.api.dto.question.SingleChoiceDTO;
import io.kluska.bsc.forms.reply.stats.service.api.exception.BadReplyTypeException;
import io.kluska.bsc.forms.reply.stats.service.api.exception.NotDefinedOptionException;
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
public class SingleChoiceReply extends Reply {
    @NonNull
    private String optionId;

    @Override
    public void validate(QuestionDTO questionDTO) {
        if (!(questionDTO instanceof SingleChoiceDTO))
            throw new BadReplyTypeException("Reply for question " + getQuestionId() +
                    " should have type " + SingleChoiceDTO.class.getSimpleName());

        validateOption((SingleChoiceDTO) questionDTO);
    }

    private void validateOption(SingleChoiceDTO singleChoiceQuestion) {
        boolean isOptionIdValid = singleChoiceQuestion.getOptions().stream()
                .map(OptionDTO::getId)
                .anyMatch(questionOptionId -> questionOptionId.equals(optionId));

        if (!isOptionIdValid)
            throw new NotDefinedOptionException("Option with id: " + optionId + " is not defined in question schema.");
    }
}
