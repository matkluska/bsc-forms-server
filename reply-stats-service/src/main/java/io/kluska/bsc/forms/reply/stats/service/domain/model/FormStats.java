package io.kluska.bsc.forms.reply.stats.service.domain.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author Mateusz Kluska
 */
@Document(collection = "formStats")
@Data
@Builder
public class FormStats {
    @Id
    private String formId;
    private List<QuestionStats> questionStats;
}
