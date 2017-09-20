package io.kluska.bsc.forms.exception.handling.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Mateusz Kluska
 */
@Data
@NoArgsConstructor
public class ErrorInfo {

    @JsonProperty("error")
    private String error;
    @JsonProperty("error_description")
    private String errorDescription;

    public ErrorInfo(Throwable ex, String errorDescription) {
        super();
        this.error = ex.getClass().getSimpleName();
        this.errorDescription = errorDescription;
    }
}
