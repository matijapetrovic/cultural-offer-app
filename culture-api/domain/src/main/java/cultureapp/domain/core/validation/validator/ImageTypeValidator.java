package cultureapp.domain.core.validation.validator;

import cultureapp.domain.core.validation.annotation.ImageType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageTypeValidator implements
        ConstraintValidator<ImageType, String> {
    @Override
    public boolean isValid(String imageType, ConstraintValidatorContext constraintValidatorContext) {
        return imageType != null
                && (imageType.equalsIgnoreCase("image/jpeg")
                    || imageType.equalsIgnoreCase("image/jpg")
                    || imageType.equalsIgnoreCase("image/png"));
    }
}
