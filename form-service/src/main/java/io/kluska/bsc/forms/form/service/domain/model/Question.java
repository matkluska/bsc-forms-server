package io.kluska.bsc.forms.form.service.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

/**
 * @author Mateusz Kluska
 */
@Data
@NoArgsConstructor
public abstract class Question {
    @NonNull
    private final String id = UUID.randomUUID().toString();
    @NonNull
    @NotEmpty
    private String question;
    private boolean isRequired;
}
