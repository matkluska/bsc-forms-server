package io.kluska.bsc.forms.form.api.dto.question;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.kluska.bsc.forms.form.api.dto.QuestionDTO;

/**
 * @author Mateusz Kluska
 */
@JsonTypeName(value = "LONG_TEXT")
public class ShortTextDTO extends QuestionDTO {
}
