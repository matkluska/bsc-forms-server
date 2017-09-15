package io.kluska.bsc.forms.form.api.dto.question;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.kluska.bsc.forms.form.api.dto.QuestionDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author Mateusz Kluska
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LinearScaleDTO extends QuestionDTO {
    @Min(value = 0)
    private int min;
    @Max(value = 100)
    private int max;
    private String minLabel;
    private String maxLabel;
}
