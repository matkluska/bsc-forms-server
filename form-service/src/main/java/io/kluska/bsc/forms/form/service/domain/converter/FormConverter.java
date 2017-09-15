package io.kluska.bsc.forms.form.service.domain.converter;

import io.kluska.bsc.forms.form.api.dto.FormDTO;
import io.kluska.bsc.forms.form.service.domain.model.Form;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Mateusz Kluska
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FormConverter implements Function<Form, FormDTO> {
    private final QuestionConverter questionConverter;

    @Override
    public FormDTO apply(Form form) {
        FormDTO formDTO = new FormDTO(form.getTitle(),
                form.getQuestions().stream()
                        .map(questionConverter)
                        .collect(Collectors.toList()));
        formDTO.setId(form.getId());
        formDTO.setDesc(form.getDesc());

        return formDTO;
    }
}
