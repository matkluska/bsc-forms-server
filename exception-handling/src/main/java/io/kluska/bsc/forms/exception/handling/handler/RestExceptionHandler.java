package io.kluska.bsc.forms.exception.handling.handler;

import io.kluska.bsc.forms.exception.handling.error.ClientErrorException;
import io.kluska.bsc.forms.exception.handling.error.ErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Mateusz Kluska
 */
@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Handled MethodArgumentNotValidException: ", ex);
        BindingResult result = ex.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        Map<String, String> fieldMessageMap = fieldErrors.stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage,
                        (v1, v2) -> v1 + " & " + v2));

        return new ResponseEntity<>(fieldMessageMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ClientErrorException.class})
    public ResponseEntity<ErrorInfo> handleClientErrorException(ClientErrorException ex) {
        log.error("Handled ClientErrorException: ", ex);
        return new ResponseEntity<>(ex.getInfo(), ex.getInfo().getStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleOtherException(Exception ex) {
        log.error("Handled Exception: ", ex);
        if (ex instanceof ClientErrorException) {
            return handleClientErrorException((ClientErrorException) ex);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}