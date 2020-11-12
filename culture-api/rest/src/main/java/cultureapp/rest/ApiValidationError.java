package cultureapp.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.ConstraintViolation;

@Data
@EqualsAndHashCode(callSuper =  false)
@AllArgsConstructor
public class ApiValidationError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    ApiValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }

    ApiValidationError(ConstraintViolation<?> constraintViolation) {
        object = constraintViolation.getRootBeanClass().getSimpleName();
        field = constraintViolation.getPropertyPath().toString();
        rejectedValue = constraintViolation.getInvalidValue();
        message = constraintViolation.getMessage();
    }
}
