package io.kluska.bsc.forms.form.service.api.dto.question;

import io.kluska.bsc.forms.form.service.api.dto.OptionDTO;
import io.kluska.bsc.forms.form.service.api.dto.QuestionDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Mateusz Kluska
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class MultipleChoiceDTO extends QuestionDTO {
    @NonNull
    @NotNull
    @Valid
    private List<OptionDTO> options;
}
