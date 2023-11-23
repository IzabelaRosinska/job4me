package miwm.job4me.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PdfGenerationException extends RuntimeException {

    public PdfGenerationException() {
        super();
    }

    public PdfGenerationException(String message) {
        super(message);
    }

    public PdfGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
