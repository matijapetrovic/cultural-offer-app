package cultureapp.domain.core.validation.annotation;

import cultureapp.domain.core.validation.validator.LatitudeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LatitudeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Latitude {
    String message() default "latitude must be a double between -90 and 90";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}