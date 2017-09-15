package io.kluska.bsc.forms.form.service.domain.converter;

import io.kluska.bsc.forms.form.api.dto.QuestionDTO;
import io.kluska.bsc.forms.form.api.dto.question.LinearScaleDTO;
import io.kluska.bsc.forms.form.api.dto.question.LongTextDTO;
import io.kluska.bsc.forms.form.api.dto.question.MultipleChoiceDTO;
import io.kluska.bsc.forms.form.api.dto.question.ShortTextDTO;
import io.kluska.bsc.forms.form.api.dto.question.SingleChoiceDTO;
import io.kluska.bsc.forms.form.service.domain.model.Question;
import io.kluska.bsc.forms.form.service.domain.model.questions.LinearScale;
import io.kluska.bsc.forms.form.service.domain.model.questions.LongText;
import io.kluska.bsc.forms.form.service.domain.model.questions.MultipleChoice;
import io.kluska.bsc.forms.form.service.domain.model.questions.ShortText;
import io.kluska.bsc.forms.form.service.domain.model.questions.SingleChoice;
import io.vavr.API;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

/**
 * @author Mateusz Kluska
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class QuestionDTOConverter implements Function<QuestionDTO, Question> {
    @NonNull
    private final ModelMapper modelMapper;

    @Override
    public Question apply(@NonNull QuestionDTO questionDTO) {
        return Match(questionDTO).of(
                API.Case($(instanceOf(ShortTextDTO.class)), modelMapper.map(questionDTO, ShortText.class)),
                API.Case($(instanceOf(LongTextDTO.class)), modelMapper.map(questionDTO, LongText.class)),
                API.Case($(instanceOf(SingleChoiceDTO.class)), modelMapper.map(questionDTO, SingleChoice.class)),
                API.Case($(instanceOf(MultipleChoiceDTO.class)), modelMapper.map(questionDTO, MultipleChoice.class)),
                API.Case($(instanceOf(LinearScaleDTO.class)), modelMapper.map(questionDTO, LinearScale.class)),
                Case($(), () -> {
                    throw new RuntimeException("Unsupported question type");
                }));
    }
}
