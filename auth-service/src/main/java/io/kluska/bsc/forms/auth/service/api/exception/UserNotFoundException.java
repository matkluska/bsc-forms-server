package io.kluska.bsc.forms.auth.service.api.exception;

/**
 * @author Mateusz Kluska
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found.");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
