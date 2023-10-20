package miwm.job4me.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidSizeException extends RuntimeException {

    public InvalidSizeException() {
        super();
    }

    public InvalidSizeException(String message) {
        super(message);
    }

    public InvalidSizeException(String message, Throwable cause) {
        super(message, cause);
    }
}
