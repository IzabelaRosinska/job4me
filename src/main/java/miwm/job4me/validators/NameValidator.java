package miwm.job4me.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class NameValidator implements ConstraintValidator<ValidName, String> {

    private static List<String> forbiddenNames = Arrays.asList("admin", "user", "client", "provider");

    @Override
    public void initialize(ValidName constraintAnnotation) {}

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context){
        return (validateName(email));
    }
    private boolean validateName(String userName) {
        return !forbiddenNames.contains(userName);
    }
}