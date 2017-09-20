package io.kluska.bsc.forms.auth.service.api.exception;

/**
 * @author Mateusz Kluska
 */
public class UsernameAlreadyInUseException extends RuntimeException {
    public UsernameAlreadyInUseException() {
        super("Username already in use.");
    }

    public UsernameAlreadyInUseException(String message) {
        super(message);
    }
}
