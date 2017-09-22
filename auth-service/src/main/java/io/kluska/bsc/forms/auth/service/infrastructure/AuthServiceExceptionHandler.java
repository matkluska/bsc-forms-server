package io.kluska.bsc.forms.auth.service.infrastructure;

import io.kluska.bsc.forms.auth.service.api.exception.UserNotFoundException;
import io.kluska.bsc.forms.auth.service.api.exception.UsernameAlreadyUsedException;
import io.kluska.bsc.forms.exception.handling.error.ErrorInfo;
import io.kluska.bsc.forms.exception.handling.handler.RestExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Mateusz Kluska
 */
@ControllerAdvice
public class AuthServiceExceptionHandler extends RestExceptionHandler {
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = UsernameAlreadyUsedException.class)
    @ResponseBody
    protected ErrorInfo handleUsernameAlreadyInUseException(UsernameAlreadyUsedException ex) {
        return handleException(ex);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseBody
    protected ErrorInfo handleUserNotFoundException(UserNotFoundException ex) {
        return handleException(ex);
    }
}
