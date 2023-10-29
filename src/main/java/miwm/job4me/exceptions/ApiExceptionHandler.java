package miwm.job4me.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<?> handleInvalidArgumentException(InvalidArgumentException e, WebRequest request) {
        ApiException apiException = ApiException.builder()
                .title("Invalid argument")
                .source(request.getDescription(false))
                .details(e.getMessage()).build();

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementFoundException.class)
    public ResponseEntity<?> handleNoSuchElementFoundException(NoSuchElementFoundException e, WebRequest request) {
        ApiException apiException = ApiException.builder()
                .title("No such element found")
                .source(request.getDescription(false))
                .details(e.getMessage()).build();

        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }
}
