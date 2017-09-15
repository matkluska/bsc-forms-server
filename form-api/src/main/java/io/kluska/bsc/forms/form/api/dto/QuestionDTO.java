package io.kluska.bsc.forms.form.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.kluska.bsc.forms.form.api.dto.question.LinearScaleDTO;
import io.kluska.bsc.forms.form.api.dto.question.LongTextDTO;
import io.kluska.bsc.forms.form.api.dto.question.MultipleChoiceDTO;
import io.kluska.bsc.forms.form.api.dto.question.ShortTextDTO;
import io.kluska.bsc.forms.form.api.dto.question.SingleChoiceDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Mateusz Kluska
 */
@Data
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true)
@JsonSubTypes({
        @Type(value = ShortTextDTO.class, name = "SHORT_TEXT"),
        @Type(value = LongTextDTO.class, name = "LONG_TEXT"),
        @Type(value = SingleChoiceDTO.class, name = "SINGLE_CHOICE"),
        @Type(value = MultipleChoiceDTO.class, name = "MULTIPLE_CHOICE"),
        @Type(value = LinearScaleDTO.class, name = "LINEAR_SCALE")
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class QuestionDTO {
    private String id;
    @NonNull
    @NotNull
    @NotEmpty
    private String question;
    @NotNull
    private boolean isRequired;
}
