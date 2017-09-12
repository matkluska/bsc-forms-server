package io.kluska.bsc.forms.form.service.domain.model.questions;

import io.kluska.bsc.forms.form.service.domain.model.Question;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Mateusz Kluska
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LinearScale extends Question {
    private int min;
    private int max;
    private String minLabel;
    private String maxLabel;
}
