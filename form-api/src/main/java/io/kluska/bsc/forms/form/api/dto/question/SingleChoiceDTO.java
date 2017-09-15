package io.kluska.bsc.forms.form.api.dto.question;

import io.kluska.bsc.forms.form.api.dto.OptionDTO;
import io.kluska.bsc.forms.form.api.dto.QuestionDTO;
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
public class SingleChoiceDTO extends QuestionDTO {
    @NonNull
    @NotNull
    @Valid
    private List<OptionDTO> options;
}
