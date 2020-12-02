package cultureapp.domain.core.validation.annotation;

import cultureapp.domain.core.validation.validator.ImageTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ImageTypeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ImageType {
    String message() default "must be one of jpeg, jpg or png";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
