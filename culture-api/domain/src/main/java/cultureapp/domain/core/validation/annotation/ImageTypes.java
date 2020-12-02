package cultureapp.domain.core.validation.annotation;

import cultureapp.domain.core.validation.validator.ImageTypesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ImageTypesValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ImageTypes {
    String message() default "each image type must be one of jpeg, jpg or png";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
