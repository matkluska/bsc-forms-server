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
public class QuestionConverter implements Function<Question, QuestionDTO> {
    @NonNull
    private final ModelMapper modelMapper;

    @Override
    public QuestionDTO apply(@NonNull Question question) {
        return Match(question).of(
                API.Case(API.$(instanceOf(ShortText.class)), modelMapper.map(question, ShortTextDTO.class)),
                API.Case(API.$(instanceOf(LongText.class)), modelMapper.map(question, LongTextDTO.class)),
                API.Case(API.$(instanceOf(SingleChoice.class)), modelMapper.map(question, SingleChoiceDTO.class)),
                API.Case(API.$(instanceOf(MultipleChoice.class)), modelMapper.map(question, MultipleChoiceDTO.class)),
                API.Case(API.$(instanceOf(LinearScale.class)), modelMapper.map(question, LinearScaleDTO.class)),
                Case($(), () -> {
                    throw new RuntimeException("Unsupported question type");
                }));
    }
}
