package miwm.job4me.validators.fields;

import miwm.job4me.model.event.JobFair;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, JobFair> {

    @Override
    public void initialize(ValidDateRange constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(JobFair jobFair, ConstraintValidatorContext constraintValidatorContext) {
        if (jobFair == null) {
            return true;
        }

        if (jobFair.getDateStart() == null || jobFair.getDateEnd() == null) {
            return true;
        }

        return jobFair.getDateStart().isBefore(jobFair.getDateEnd());
    }
}
