package cultureapp.domain.core.validation.annotation;

import cultureapp.domain.core.validation.validator.LongitudeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LongitudeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Longitude {
    String message() default "longitude must be a double between -180 and 180";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
