package io.kluska.bsc.forms.reply.stats.service.api.exception;

/**
 * @author Mateusz Kluska
 */
public class LackOfRequiredReplyException extends RuntimeException {
    public LackOfRequiredReplyException() {
        super("Lack of required reply for at least one question.");
    }

    public LackOfRequiredReplyException(String message) {
        super(message);
    }
}
