package io.kluska.bsc.forms.reply.stats.service.api.exception;

/**
 * @author Mateusz Kluska
 */
public class FormStatsNotFoundException extends RuntimeException {
    public FormStatsNotFoundException() {
        super("Form's stats not exist");
    }

    public FormStatsNotFoundException(String message) {
        super(message);
    }
}
