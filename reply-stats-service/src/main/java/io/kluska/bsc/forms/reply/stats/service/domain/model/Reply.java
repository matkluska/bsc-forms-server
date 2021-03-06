package io.kluska.bsc.forms.reply.stats.service.domain.model;

import io.kluska.bsc.forms.form.api.dto.QuestionDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Mateusz Kluska
 */
@Document(collection = Reply.REPLIES_COLLECTION)
@Data
@NoArgsConstructor
public abstract class Reply {
    public static final String REPLIES_COLLECTION = "replies";

    @Id
    private String id;
    @NonNull
    private String formId;
    @NonNull
    private String questionId;

    public abstract void validate(QuestionDTO questionDTO);
}
