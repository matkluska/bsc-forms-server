package io.kluska.bsc.forms.form.service.api.controller;

import io.kluska.bsc.forms.form.api.dto.FormDTO;
import io.kluska.bsc.forms.form.service.api.exception.FormNotFoundException;
import io.kluska.bsc.forms.form.service.domain.converter.FormConverter;
import io.kluska.bsc.forms.form.service.domain.converter.FormDTOConverter;
import io.kluska.bsc.forms.form.service.domain.model.Form;
import io.kluska.bsc.forms.form.service.domain.service.FormService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mateusz Kluska
 */
@RestController
@RequestMapping(path = "/forms")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FormController {
    private final FormService formService;
    private final FormConverter formConverter;
    private final FormDTOConverter formDTOConverter;

    @GetMapping
    public List<FormDTO> findFormByUsername(@NonNull Principal principal) {
        return formService.findByUsername(principal.getName()).stream()
                .map(formConverter)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public FormDTO findFormById(@PathVariable(name = "id") String id) {
        return formService.findOneById(id)
                .map(formConverter)
                .orElseThrow(FormNotFoundException::new);
    }

    @PostMapping
    public void addForm(@RequestBody @Valid FormDTO formDTO, @NonNull Principal principal) {
        Form form = formDTOConverter.apply(formDTO, principal.getName());
        formService.addForm(form);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteForm(@PathVariable(name = "id") String id) {
        formService.deleteForm(id);
    }
}
