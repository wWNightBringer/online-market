package com.app.common.constraints;

import com.app.common.exception.InvalidEmailException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.validator.routines.EmailValidator;

public class ValidEmailValidator implements ConstraintValidator<ValidEmail, String> {

    private String message;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (EmailValidator.getInstance().isValid(email)) {
            return true;
        }
        throw new InvalidEmailException(message);
    }

    @Override
    public void initialize(ValidEmail constraintAnnotation) {
        message = constraintAnnotation.message();
    }
}
