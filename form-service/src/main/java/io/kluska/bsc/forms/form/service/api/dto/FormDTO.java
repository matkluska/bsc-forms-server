package io.kluska.bsc.forms.form.service.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NonNull;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Mateusz Kluska
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FormDTO {
    private String id;
    @NonNull
    @NotNull
    @NotEmpty
    private String title;
    private String desc;
    @NonNull
    @NotNull
    @Valid
    private List<QuestionDTO> questions;
}
