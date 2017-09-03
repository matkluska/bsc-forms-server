package io.kluska.bsc.forms.auth.service.api.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

/**
 * @author Mateusz Kluska
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorInfo {
    //UserController
    USERNAME_INVALID(4000, "Username length must be between 3 and 15 characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(4001, "Password length must be between 6 and 15 characters", HttpStatus.BAD_REQUEST),
    MAIL_ALREADY_IN_USE(4002, "Mail already in use", HttpStatus.CONFLICT),
    MAIL_INVALID(4003, "Invalid mail", HttpStatus.BAD_REQUEST),
    USERNAME_ALREADY_IN_USE(4004, "Username already in use", HttpStatus.CONFLICT),
    USER_NOT_FOUND(4005, "User not found", HttpStatus.NOT_FOUND);

    private final int errCode;
    private final String errDesc;
    private final HttpStatus status;

    ErrorInfo(int errCode, String errDesc, HttpStatus status) {
        this.errCode = errCode;
        this.errDesc = errDesc;
        this.status = status;
    }

    public int getErrCode() {
        return errCode;
    }

    public String getErrDesc() {
        return errDesc;
    }

    @JsonIgnore
    public HttpStatus getStatus() {
        return status;
    }
}
