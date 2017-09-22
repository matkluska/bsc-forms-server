package io.kluska.bsc.forms.reply.stats.service.infrastructure;

import io.kluska.bsc.forms.exception.handling.error.ErrorInfo;
import io.kluska.bsc.forms.exception.handling.handler.RestExceptionHandler;
import io.kluska.bsc.forms.reply.stats.service.api.exception.BadReplyTypeException;
import io.kluska.bsc.forms.reply.stats.service.api.exception.InconsistentFormIdsException;
import io.kluska.bsc.forms.reply.stats.service.api.exception.LackOfRequiredReplyException;
import io.kluska.bsc.forms.reply.stats.service.api.exception.NotDefinedQuestionException;
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

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(value = LackOfRequiredReplyException.class)
    @ResponseBody
    protected ErrorInfo handleLackOfRequiredReplyException(LackOfRequiredReplyException ex) {
        return handleException(ex);
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(value = BadReplyTypeException.class)
    @ResponseBody
    protected ErrorInfo handleBadReplyTypeException(BadReplyTypeException ex) {
        return handleException(ex);
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(value = NotDefinedQuestionException.class)
    @ResponseBody
    protected ErrorInfo handleNotDefinedQuestionException(NotDefinedQuestionException ex) {
        return handleException(ex);
    }
}
