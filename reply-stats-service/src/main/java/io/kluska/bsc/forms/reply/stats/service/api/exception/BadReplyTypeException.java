package io.kluska.bsc.forms.reply.stats.service.api.exception;

/**
 * @author Mateusz Kluska
 */
public class BadReplyTypeException extends RuntimeException {
    public BadReplyTypeException() {
        super("Reply type is different in saved form.");
    }

    public BadReplyTypeException(String message) {
        super(message);
    }
}
