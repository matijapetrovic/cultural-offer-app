package cultureapp.domain.core.validation.validator;

import cultureapp.domain.core.validation.annotation.Latitude;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LatitudeValidator implements
        ConstraintValidator<Latitude, Double> {
    @Override
    public boolean isValid(Double latitude, ConstraintValidatorContext constraintValidatorContext) {
        return latitude != null && latitude <= 90.0 && latitude >= -90.0;
    }
}
