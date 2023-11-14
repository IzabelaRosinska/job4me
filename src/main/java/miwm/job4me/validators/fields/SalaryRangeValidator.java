package miwm.job4me.validators.fields;

import miwm.job4me.model.offer.JobOffer;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SalaryRangeValidator implements ConstraintValidator<ValidSalaryRange, JobOffer> {
    @Override
    public void initialize(ValidSalaryRange constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(JobOffer jobOffer, ConstraintValidatorContext context) {
        if (jobOffer == null) {
            return true;
        }

        Integer salaryFrom = jobOffer.getSalaryFrom();
        Integer salaryTo = jobOffer.getSalaryTo();

        if (salaryFrom == null || salaryTo == null) {
            return true;
        }

        return salaryFrom < salaryTo;
    }
}