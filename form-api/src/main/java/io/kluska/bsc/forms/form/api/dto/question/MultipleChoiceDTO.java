package io.kluska.bsc.forms.form.api.dto.question;

import cz.jirutka.validator.collection.constraints.EachNotNull;
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
public class MultipleChoiceDTO extends QuestionDTO {
    @NonNull
    @NotNull
    @EachNotNull
    @Valid
    private List<OptionDTO> options;
}
