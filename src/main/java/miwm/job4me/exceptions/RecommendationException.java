package miwm.job4me.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class RecommendationException extends RuntimeException {
    public RecommendationException() {
        super();
    }

    public RecommendationException(String message) {
        super(message);
    }

    public RecommendationException(String message, Throwable cause) {
        super(message, cause);
    }
}
