package io.kluska.bsc.forms.reply.stats.service.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.kluska.bsc.forms.reply.stats.service.api.dto.replies.LinearScaleReplyDTO;
import io.kluska.bsc.forms.reply.stats.service.api.dto.replies.LongTextReplyDTO;
import io.kluska.bsc.forms.reply.stats.service.api.dto.replies.MultipleChoiceReplyDTO;
import io.kluska.bsc.forms.reply.stats.service.api.dto.replies.ShortTextReplyDTO;
import io.kluska.bsc.forms.reply.stats.service.api.dto.replies.SingleChoiceReplyDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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
        @JsonSubTypes.Type(value = ShortTextReplyDTO.class, name = "SHORT_TEXT_REPLY"),
        @JsonSubTypes.Type(value = LongTextReplyDTO.class, name = "LONG_TEXT_REPLY"),
        @JsonSubTypes.Type(value = SingleChoiceReplyDTO.class, name = "SINGLE_CHOICE_REPLY"),
        @JsonSubTypes.Type(value = MultipleChoiceReplyDTO.class, name = "MULTIPLE_CHOICE_REPLY"),
        @JsonSubTypes.Type(value = LinearScaleReplyDTO.class, name = "LINEAR_SCALE_REPLY")
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class ReplyDTO {
    @NonNull
    @NotNull
    private String questionId;
}
