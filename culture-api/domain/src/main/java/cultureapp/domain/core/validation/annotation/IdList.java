package cultureapp.domain.core.validation.annotation;

import cultureapp.domain.core.validation.validator.IdListValidator;
import cultureapp.domain.core.validation.validator.ImageTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IdListValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IdList {
    String message() default "all ids must be positive longs";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
