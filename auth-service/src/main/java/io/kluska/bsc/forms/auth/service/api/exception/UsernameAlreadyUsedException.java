package io.kluska.bsc.forms.auth.service.api.exception;

/**
 * @author Mateusz Kluska
 */
public class UsernameAlreadyUsedException extends RuntimeException {
    public UsernameAlreadyUsedException() {
        super("Username already in use.");
    }

    public UsernameAlreadyUsedException(String message) {
        super(message);
    }
}
