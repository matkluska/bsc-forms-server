package io.kluska.bsc.forms.auth.service.api.exception;

/**
 * @author Mateusz Kluska
 */
public class ClientErrorException extends RuntimeException {
    private ErrorInfo info;

    public ClientErrorException(ErrorInfo info) {
        this.info = info;
    }

    public ErrorInfo getInfo() {
        return info;
    }
}
