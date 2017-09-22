package io.kluska.bsc.forms.reply.stats.service.domain.model.replies;

import io.kluska.bsc.forms.form.api.dto.OptionDTO;
import io.kluska.bsc.forms.form.api.dto.QuestionDTO;
import io.kluska.bsc.forms.form.api.dto.question.MultipleChoiceDTO;
import io.kluska.bsc.forms.reply.stats.service.api.exception.BadReplyTypeException;
import io.kluska.bsc.forms.reply.stats.service.api.exception.NotDefinedOptionException;
import io.kluska.bsc.forms.reply.stats.service.domain.model.Reply;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mateusz Kluska
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MultipleChoiceReply extends Reply {
    @NonNull
    private List<String> optionIds;

    @Override
    public void validate(QuestionDTO questionDTO) {
        if (!(questionDTO instanceof MultipleChoiceDTO))
            throw new BadReplyTypeException("Reply for question " + getQuestionId() +
                    " should have type " + MultipleChoiceDTO.class.getSimpleName());

        validateOption((MultipleChoiceDTO) questionDTO);
    }

    private void validateOption(MultipleChoiceDTO multipleChoiceDTO) {
        List<String> questionOptionIds = multipleChoiceDTO.getOptions().stream()
                .map(OptionDTO::getId)
                .collect(Collectors.toList());

        if (!questionOptionIds.containsAll(optionIds))
            throw new NotDefinedOptionException();
    }
}
