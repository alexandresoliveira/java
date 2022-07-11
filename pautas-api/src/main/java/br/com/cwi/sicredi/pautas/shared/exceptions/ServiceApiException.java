package br.com.cwi.sicredi.pautas.shared.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ServiceApiException extends RuntimeException {

    @Getter
    private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public ServiceApiException(String message) {
        super(message);
    }

    public ServiceApiException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
