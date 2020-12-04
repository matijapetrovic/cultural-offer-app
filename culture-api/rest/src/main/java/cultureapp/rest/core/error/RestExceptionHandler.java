package cultureapp.rest.core.error;

import cultureapp.domain.account.exception.AccountAlreadyExists;
import cultureapp.domain.category.exception.CategoryAlreadyExists;
import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.review.exception.ReviewNotFoundException;
import cultureapp.domain.image.exception.ImageNotFoundException;
import cultureapp.domain.subcategory.exception.SubcategoryAlreadyExists;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice("cultureapp.rest")
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ReviewNotFoundException.class)
    protected ResponseEntity<Object> handleReviewNotFound(
            ReviewNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        return buildResponse(apiError);
    }

    @ExceptionHandler(ImageNotFoundException.class)
    protected ResponseEntity<Object> handleImageNotFoundException(
            ImageNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        return buildResponse(apiError);
    }

    @ExceptionHandler(SubcategoryAlreadyExists.class)
    protected ResponseEntity<Object> handleSubcategoryAlreadyExists(
            SubcategoryAlreadyExists ex) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex.getMessage(), ex);
        return buildResponse(apiError);
    }

    @ExceptionHandler(CategoryAlreadyExists.class)
    protected ResponseEntity<Object> handleCategoryAlreadyExists(
            CategoryAlreadyExists ex) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex.getMessage(), ex);
        return buildResponse(apiError);
    }

    @ExceptionHandler(AccountAlreadyExists.class)
    protected ResponseEntity<Object> handleUserAlreadyExists(
            AccountAlreadyExists ex) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex.getMessage(), ex);
        return buildResponse(apiError);
    }

    @ExceptionHandler(SubcategoryNotFoundException.class)
    protected ResponseEntity<Object> handleSubcategoryNotFound(
            SubcategoryNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        return buildResponse(apiError);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    protected ResponseEntity<Object> handleCategoryNotFound(
            CategoryNotFoundException ex) {
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

    @ExceptionHandler(IOException.class)
    protected ResponseEntity<Object> handleCategoryNotFound(
            IOException ex) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        return buildResponse(apiError);
    }

    private ResponseEntity<Object> buildResponse(ApiError apiError) {
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }
}
