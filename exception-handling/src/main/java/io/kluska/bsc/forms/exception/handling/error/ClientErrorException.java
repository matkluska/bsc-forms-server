package io.kluska.bsc.forms.exception.handling.error;

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
