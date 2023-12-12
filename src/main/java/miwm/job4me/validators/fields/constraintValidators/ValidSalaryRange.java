package miwm.job4me.validators.fields.constraintValidators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SalaryRangeValidator.class)
public @interface ValidSalaryRange {
    String message() default "Invalid JobOffer salary range";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
