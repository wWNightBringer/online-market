package com.app.common.constraints;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.validator.routines.EmailValidator;

public class ValidEmailValidator implements ConstraintValidator<ValidEmail, String> {

    private String messageError;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (EmailValidator.getInstance().isValid(email)) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context
            .buildConstraintViolationWithTemplate(messageError)
            .addConstraintViolation();
        return false;
    }

    @Override
    public void initialize(ValidEmail constraintAnnotation) {
        messageError = constraintAnnotation.message();
    }
}
