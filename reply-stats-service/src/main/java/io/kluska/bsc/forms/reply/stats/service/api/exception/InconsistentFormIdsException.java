package io.kluska.bsc.forms.reply.stats.service.api.exception;

/**
 * @author Mateusz Kluska
 */
public class InconsistentFormIdsException extends RuntimeException {
    public InconsistentFormIdsException() {
        super("Replies has different form ids");
    }

    public InconsistentFormIdsException(String message) {
        super(message);
    }
}
