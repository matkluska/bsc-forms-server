package io.kluska.bsc.forms.form.service.api.exception;

/**
 * @author Mateusz Kluska
 */
public class FormNotFoundException extends RuntimeException {
    public FormNotFoundException() {
        super("Form not found.");
    }

    public FormNotFoundException(String message) {
        super(message);
    }
}
