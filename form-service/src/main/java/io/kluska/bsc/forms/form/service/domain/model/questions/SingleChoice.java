package io.kluska.bsc.forms.form.service.domain.model.questions;

import io.kluska.bsc.forms.form.service.domain.model.Option;
import io.kluska.bsc.forms.form.service.domain.model.Question;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

/**
 * @author Mateusz Kluska
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SingleChoice extends Question {
    @NonNull
    private List<Option> options;
}
