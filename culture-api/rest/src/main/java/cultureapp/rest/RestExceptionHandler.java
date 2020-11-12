package cultureapp.rest;

import cultureapp.domain.core.example.exceptions.ExampleNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice("cultureapp.rest")
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ExampleNotFoundException.class)
    protected ResponseEntity<Object> handleExampleNotFound(
            ExampleNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        return buildResponse(apiError);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolated(
            ConstraintViolationException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Constraints violated", ex);

        for (ConstraintViolation<?> cv : ex.getConstraintViolations())
            apiError.addSubError(new ApiValidationError(cv));

        return buildResponse(apiError);
    }

    private ResponseEntity<Object> buildResponse(ApiError apiError) {
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }
}
