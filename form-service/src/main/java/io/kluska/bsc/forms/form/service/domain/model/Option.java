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
public class Option {
    @NonNull
    @NotEmpty
    private final String id = UUID.randomUUID().toString();
    @NotEmpty
    @NonNull
    private String option;
}
