package io.kluska.bsc.forms.reply.stats.service.api.exception;

/**
 * @author Mateusz Kluska
 */
public class NotDefinedQuestionException extends RuntimeException {
    public NotDefinedQuestionException() {
        super("At least one reply is belong to not exist question in form template");
    }

    public NotDefinedQuestionException(String message) {
        super(message);
    }
}
