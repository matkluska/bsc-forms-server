package io.kluska.bsc.forms.form.service.domain.service;

import io.kluska.bsc.forms.form.service.domain.model.Form;
import io.kluska.bsc.forms.form.service.domain.repository.FormRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Mateusz Kluska
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FormService {
    private final FormRepository repository;

    public void addForm(Form form) {
        repository.save(form);
    }

    public Optional<Form> findOneById(@NonNull String id) {
        return repository.findOneById(id);
    }

    public List<Form> findByUsername(@NonNull String username) {
        return repository.findByUsername(username);
    }
}
