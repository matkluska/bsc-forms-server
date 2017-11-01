package io.kluska.bsc.forms.form.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormDTO {
    private String id;
    @NonNull
    @NotNull
    @NotEmpty
    private String title;
    private String desc;
    private long creationTime;
    @NonNull
    @NotNull
    @Valid
    private List<QuestionDTO> questions;
}
