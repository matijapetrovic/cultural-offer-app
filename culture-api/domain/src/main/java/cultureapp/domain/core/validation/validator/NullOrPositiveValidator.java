package cultureapp.domain.core.validation.validator;

import cultureapp.domain.core.validation.annotation.NullOrPositive;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NullOrPositiveValidator implements
        ConstraintValidator<NullOrPositive, Long> {
    @Override
    public boolean isValid(Long value, ConstraintValidatorContext constraintValidatorContext) {
        return value == null || value > 0;
    }
}
