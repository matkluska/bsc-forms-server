package io.kluska.bsc.forms.form.service.infrastructure;

import io.kluska.bsc.forms.exception.handling.error.ErrorInfo;
import io.kluska.bsc.forms.exception.handling.handler.RestExceptionHandler;
import io.kluska.bsc.forms.form.service.api.exception.FormNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Mateusz Kluska
 */
@ControllerAdvice
@Slf4j
public class FormServiceExceptionHandler extends RestExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = FormNotFoundException.class)
    @ResponseBody
    protected ErrorInfo handleFormNotFoundException(FormNotFoundException ex) {
        return handleException(ex);
    }
}
