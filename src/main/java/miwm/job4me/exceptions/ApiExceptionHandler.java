package miwm.job4me.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

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

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolation(ConstraintViolationException e, WebRequest request) {
        ApiException apiException = ApiException.builder()
                .title("Constraint violation")
                .source(request.getDescription(false))
                .details(extractConstraintViolationMessage(e)).build();

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolation(DataIntegrityViolationException e, WebRequest request) {
        ApiException apiException = ApiException.builder()
                .title("Data integrity violation")
                .source(request.getDescription(false))
                .details(e.getMessage()).build();

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<?> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException e, WebRequest request) {
        ApiException apiException = ApiException.builder()
                .title("Invalid data access api usage")
                .source(request.getDescription(false))
                .details(e.getMessage()).build();

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    private String extractConstraintViolationMessage(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder errorMessage = new StringBuilder();

        if (!violations.isEmpty()) {
            for (ConstraintViolation<?> violation : violations) {
                errorMessage.append(violation.getMessage()).append(".");

                if (violations.size() > 1) {
                    errorMessage.append(" ");
                }
            }

        }

        return errorMessage.toString();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest request) {
        ApiException apiException = ApiException.builder()
                .title("Method argument not valid")
                .source(request.getDescription(false))
                .details(e.getMessage()).build();

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

}
