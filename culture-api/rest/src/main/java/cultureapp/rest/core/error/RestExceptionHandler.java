package cultureapp.rest.core.error;
import cultureapp.domain.account.exception.AccountAlreadyActivatedException;
import cultureapp.domain.account.exception.AccountAlreadyExistsException;
import cultureapp.domain.account.exception.AccountNotFoundException;
import cultureapp.domain.authentication.exception.AccountNotActivatedException;
import cultureapp.domain.category.exception.CategoryCannotBeDeletedException;
import cultureapp.domain.cultural_offer.exception.CulturalOfferLocationsFilterException;
import cultureapp.domain.reply.exception.ReplyAlreadyExistException;
import cultureapp.domain.user.exception.AdminNotFoundException;
import cultureapp.domain.category.exception.CategoryAlreadyExistsException;
import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.cultural_offer.exception.SubscriptionAlreadyExistsException;
import cultureapp.domain.cultural_offer.exception.SubscriptionNotFoundException;
import cultureapp.domain.user.exception.RegularUserAlreadyExistsException;
import cultureapp.domain.review.exception.ReviewNotFoundException;
import cultureapp.domain.image.exception.ImageNotFoundException;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.news.exception.NewsNotFoundException;
import cultureapp.domain.subcategory.exception.SubcategoryAlreadyExistsException;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice("cultureapp.rest")
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<Object> handleBadCredentialsException(
            BadCredentialsException ex
    ) {
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, "Invalid username/password" ,ex);
        return buildResponse(apiError);
    }

    @ExceptionHandler(CulturalOfferLocationsFilterException.class)
    protected ResponseEntity<Object> handleCulturalOfferLocationsFilterException(
            CulturalOfferLocationsFilterException ex
    ) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        return buildResponse(apiError);
    }

    @ExceptionHandler(SubscriptionNotFoundException.class)
    protected ResponseEntity<Object> handleSubscriptionNotFound(
            SubscriptionNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        return buildResponse(apiError);
    }

    @ExceptionHandler(SubscriptionAlreadyExistsException.class)
    protected ResponseEntity<Object> handleSubscriptionAlreadyExists(
            SubscriptionAlreadyExistsException ex) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex.getMessage(), ex);
        return buildResponse(apiError);
    }

    @ExceptionHandler(RegularUserAlreadyExistsException.class)
    protected ResponseEntity<Object> handleRegularUserAlreadyExists(
            RegularUserAlreadyExistsException ex) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex.getMessage(), ex);
        return buildResponse(apiError);
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    protected ResponseEntity<Object> handleReviewNotFound(
            ReviewNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        return buildResponse(apiError);
    }

    @ExceptionHandler(ReplyAlreadyExistException.class)
    protected ResponseEntity<Object> handleReplyAlreadyExist(
            ReplyAlreadyExistException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        return buildResponse(apiError);
    }

    @ExceptionHandler(ImageNotFoundException.class)
    protected ResponseEntity<Object> handleImageNotFoundException(
            ImageNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        return buildResponse(apiError);
    }

    @ExceptionHandler(SubcategoryAlreadyExistsException.class)
    protected ResponseEntity<Object> handleSubcategoryAlreadyExists(
            SubcategoryAlreadyExistsException ex) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex.getMessage(), ex);
        return buildResponse(apiError);
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    protected ResponseEntity<Object> handleCategoryAlreadyExists(
            CategoryAlreadyExistsException ex) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex.getMessage(), ex);
        return buildResponse(apiError);
    }

    @ExceptionHandler(CategoryCannotBeDeletedException.class)
    protected ResponseEntity<Object> handleCategoryCannotBeDeleted(
            CategoryCannotBeDeletedException ex) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex.getMessage(), ex);
        return buildResponse(apiError);
    }

    @ExceptionHandler(AccountAlreadyExistsException.class)
    protected ResponseEntity<Object> handleAccountAlreadyExists(
            AccountAlreadyExistsException ex) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex.getMessage(), ex);
        return buildResponse(apiError);
    }

    @ExceptionHandler(AccountNotActivatedException.class)
    protected ResponseEntity<Object> handleAccountNotActivated(
            AccountNotActivatedException ex) {
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage(), ex);
        return buildResponse(apiError);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    protected ResponseEntity<Object> handleAccountNotFound(
            AccountNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex.getMessage(), ex);
        return buildResponse(apiError);
    }

    @ExceptionHandler(AccountAlreadyActivatedException.class)
    protected ResponseEntity<Object> handleAccountAlreadyActivated(
            AccountAlreadyActivatedException ex) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex.getMessage(), ex);
        return buildResponse(apiError);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    protected ResponseEntity<Object> handleCategoryNotFound(
            CategoryNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        return buildResponse(apiError);
    }

    @ExceptionHandler(SubcategoryNotFoundException.class)
    protected ResponseEntity<Object> handleSubcategoryNotFound(
            SubcategoryNotFoundException ex) {
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

    @ExceptionHandler(CulturalOfferNotFoundException.class)
    protected ResponseEntity<Object> handleCulturalOfferNotFound(CulturalOfferNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        return buildResponse(apiError);
    }

    @ExceptionHandler(AdminNotFoundException.class)
    protected ResponseEntity<Object> handleAdminNotFound(AdminNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        return buildResponse(apiError);
    }

    @ExceptionHandler(NewsNotFoundException.class)
    protected ResponseEntity<Object> handleNewsNotFound(NewsNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        return buildResponse(apiError);
    }

    private ResponseEntity<Object> buildResponse(ApiError apiError) {
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }
}
