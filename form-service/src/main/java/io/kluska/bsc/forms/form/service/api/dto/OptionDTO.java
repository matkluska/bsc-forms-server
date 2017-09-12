package io.kluska.bsc.forms.form.service.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;

/**
 * @author Mateusz Kluska
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OptionDTO {
    private String id;
    @NotEmpty
    @NonNull
    private String option;
}
