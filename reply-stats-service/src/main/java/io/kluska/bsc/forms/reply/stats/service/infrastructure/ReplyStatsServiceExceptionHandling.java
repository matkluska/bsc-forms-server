package io.kluska.bsc.forms.reply.stats.service.infrastructure;

import io.kluska.bsc.forms.exception.handling.error.ErrorInfo;
import io.kluska.bsc.forms.exception.handling.handler.RestExceptionHandler;
import io.kluska.bsc.forms.reply.stats.service.api.exception.InconsistentFormIdsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Mateusz Kluska
 */
@ControllerAdvice

public class ReplyStatsServiceExceptionHandling extends RestExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(value = InconsistentFormIdsException.class)
    @ResponseBody
    protected ErrorInfo handleInconsistentFormIdsException(InconsistentFormIdsException ex) {
        return handleException(ex);
    }
}
