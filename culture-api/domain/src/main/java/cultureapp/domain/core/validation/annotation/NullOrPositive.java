package cultureapp.domain.core.validation.annotation;


import cultureapp.domain.core.validation.validator.NullOrPositiveValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NullOrPositiveValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NullOrPositive {
    String message() default "value must be either null or a positive long integer";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
