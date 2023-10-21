package miwm.job4me.validators;

import miwm.job4me.model.users.RegisterData;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {}

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        RegisterData user = (RegisterData) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
