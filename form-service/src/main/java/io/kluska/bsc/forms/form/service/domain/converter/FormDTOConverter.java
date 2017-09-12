package io.kluska.bsc.forms.form.service.domain.converter;

import io.kluska.bsc.forms.form.service.api.dto.FormDTO;
import io.kluska.bsc.forms.form.service.domain.model.Form;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * @author Mateusz Kluska
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FormDTOConverter implements BiFunction<FormDTO, String, Form> {
    private final QuestionDTOConverter questionDTOConverter;

    @Override
    public Form apply(FormDTO formDTO, String username) {
        Form form = new Form(username, formDTO.getTitle(),
                formDTO.getQuestions().stream()
                        .map(questionDTOConverter)
                        .collect(Collectors.toList()));
        form.setDesc(formDTO.getDesc());

        return form;
    }
}
