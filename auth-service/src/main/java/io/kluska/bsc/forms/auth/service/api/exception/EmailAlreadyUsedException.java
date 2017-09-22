package io.kluska.bsc.forms.auth.service.api.exception;

/**
 * @author Mateusz Kluska
 */
public class EmailAlreadyUsedException extends RuntimeException {
    public EmailAlreadyUsedException() {
        super("Email already used.");
    }

    public EmailAlreadyUsedException(String message) {
        super(message);
    }
}
