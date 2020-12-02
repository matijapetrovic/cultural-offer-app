package cultureapp.domain.core.validation.validator;

import cultureapp.domain.core.validation.annotation.Longitude;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LongitudeValidator implements
        ConstraintValidator<Longitude, Double> {
    @Override
    public boolean isValid(Double longitude, ConstraintValidatorContext constraintValidatorContext) {
        return longitude != null && longitude <= 180.0 && longitude >= -180.0;
    }
}
