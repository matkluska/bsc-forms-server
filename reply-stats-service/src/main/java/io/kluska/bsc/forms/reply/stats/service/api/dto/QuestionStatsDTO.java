package io.kluska.bsc.forms.reply.stats.service.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.kluska.bsc.forms.reply.stats.service.api.dto.stats.LinearScaleStatsDTO;
import io.kluska.bsc.forms.reply.stats.service.api.dto.stats.LongTextStatsDTO;
import io.kluska.bsc.forms.reply.stats.service.api.dto.stats.MultipleChoiceStatsDTO;
import io.kluska.bsc.forms.reply.stats.service.api.dto.stats.ShortTextStatsDTO;
import io.kluska.bsc.forms.reply.stats.service.api.dto.stats.SingleChoiceStatsDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

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
        @JsonSubTypes.Type(value = ShortTextStatsDTO.class, name = "SHORT_TEXT_STATS"),
        @JsonSubTypes.Type(value = LongTextStatsDTO.class, name = "LONG_TEXT_STATS"),
        @JsonSubTypes.Type(value = SingleChoiceStatsDTO.class, name = "SINGLE_CHOICE_STATS"),
        @JsonSubTypes.Type(value = MultipleChoiceStatsDTO.class, name = "MULTIPLE_CHOICE_STATS"),
        @JsonSubTypes.Type(value = LinearScaleStatsDTO.class, name = "LINEAR_SCALE_STATS")
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class QuestionStatsDTO {
    private String questionId;
    private long repliesCount;
}
