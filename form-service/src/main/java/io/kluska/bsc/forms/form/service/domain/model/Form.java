package io.kluska.bsc.forms.form.service.domain.model;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author Mateusz Kluska
 */
@Data
@Document(collection = "forms")
public class Form {
    @Id
    private String id;
    @NonNull
    private String username;
    @NonNull
    private String title;
    private String desc;
    @NonNull
    private List<Question> questions;
}
