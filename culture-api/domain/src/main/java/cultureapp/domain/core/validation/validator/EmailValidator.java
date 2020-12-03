package cultureapp.domain.core.validation.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Email;

public class EmailValidator implements
        ConstraintValidator<Email, String> {
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return email != null
                    && email.length() > 0
                    && email.length() < 320
                    && email.matches("[^@]+@[^\\.]+\\..+");
    }
}
