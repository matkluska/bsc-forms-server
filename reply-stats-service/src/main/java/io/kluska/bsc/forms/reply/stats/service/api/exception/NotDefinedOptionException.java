package io.kluska.bsc.forms.reply.stats.service.api.exception;

/**
 * @author Mateusz Kluska
 */
public class NotDefinedOptionException extends RuntimeException {
    public NotDefinedOptionException() {
        super("At least one option is not defined in question schema.");
    }

    public NotDefinedOptionException(String message) {
        super(message);
    }
}
