package io.kluska.bsc.forms.auth.service.infrastructure.error;

import io.kluska.bsc.forms.auth.service.api.exception.ClientErrorException;
import io.kluska.bsc.forms.auth.service.api.exception.ErrorInfo;
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
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        Map<String, String> fieldMessageMap = fieldErrors.stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        return new ResponseEntity<>(fieldMessageMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ClientErrorException.class})
    public ResponseEntity<ErrorInfo> handleClientErrorException(ClientErrorException ex) {
        return new ResponseEntity<>(ex.getInfo(), ex.getInfo().getStatus());
    }
//
//    @ExceptionHandler({Exception.class})
//    public ResponseEntity<?> handleOtherException(Exception ex) {
//        if (ex instanceof ClientErrorException) {
//            return handleClientErrorException((ClientErrorException) ex);
//        }
//        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}