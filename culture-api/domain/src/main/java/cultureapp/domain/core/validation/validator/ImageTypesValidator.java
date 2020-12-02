package cultureapp.domain.core.validation.validator;

import cultureapp.domain.core.validation.annotation.ImageTypes;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ImageTypesValidator implements
        ConstraintValidator<ImageTypes, List<String>> {
    @Override
    public boolean isValid(List<String> imageTypes, ConstraintValidatorContext constraintValidatorContext) {
        return imageTypes != null && imageTypes.stream().allMatch(this::validImage);
    }

    private boolean validImage(String imageType) {
        return imageType.equalsIgnoreCase("image/jpeg")
                || imageType.equalsIgnoreCase("image/jpg")
                || imageType.equalsIgnoreCase("image/png");
    }
}
