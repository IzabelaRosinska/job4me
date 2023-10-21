package miwm.job4me.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidLengthException extends RuntimeException {

    public InvalidLengthException() {
        super();
    }

    public InvalidLengthException(String message) {
        super(message);
    }

    public InvalidLengthException(String message, Throwable cause) {
        super(message, cause);
    }
}
