package cultureapp.domain.core.validation.validator;

import cultureapp.domain.core.validation.annotation.IdList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class IdListValidator implements
        ConstraintValidator<IdList, List<Long>> {
    @Override
    public boolean isValid(List<Long> idList, ConstraintValidatorContext constraintValidatorContext) {
        return idList != null && idList.stream().allMatch(id -> id > 0);
    }
}
