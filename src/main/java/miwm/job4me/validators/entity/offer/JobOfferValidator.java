package miwm.job4me.validators.entity.offer;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.JobOffer;
import miwm.job4me.validators.fields.IntegerRangeValidator;
import miwm.job4me.validators.fields.ListValidator;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.model.offer.JobOfferDto;
import org.springframework.stereotype.Component;

@Component
public class JobOfferValidator {
    private final IntegerRangeValidator integerRangeValidator;
    private final StringFieldValidator stringFieldValidator;
    private final ListValidator listValidator;
    private final int MIN_OFFER_NAME_LENGTH = 1;
    private final int MAX_OFFER_NAME_LENGTH = 120;
    private final int MIN_WORKING_TIME_LENGTH = 1;
    private final int MAX_WORKING_TIME_LENGTH = 20;
    private final int MIN_DUTIES_LENGTH = 1;
    private final int MAX_DUTIES_LENGTH = 1000;
    private final int MIN_DESCRIPTION_LENGTH = 1;
    private final int MAX_DESCRIPTION_LENGTH = 1000;

    private final int MIN_SIZE_INDUSTRIES = 1;
    private final int MAX_SIZE_INDUSTRIES = 10;
    private final int MAX_LENGTH_INDUSTRY = 100;

    private final int MIN_SIZE_LOCALIZATIONS = 1;
    private final int MAX_SIZE_LOCALIZATIONS = 10;
    private final int MAX_LENGTH_LOCALIZATION = 50;

    private final int MIN_SIZE_EMPLOYMENT_FORMS = 1;
    private final int MAX_LENGTH_EMPLOYMENT_FORM = 50;

    private final int MAX_LENGTH_CONTRACT_TYPE = 50;

    private final int MAX_LENGTH_LEVEL = 50;

    private final int MIN_SIZE_REQUIREMENTS = 1;
    private final int MAX_SIZE_REQUIREMENTS = 15;
    private final int MAX_LENGTH_REQUIREMENT = 250;

    private final int MAX_SIZE_EXTRA_SKILLS = 10;
    private final int MAX_LENGTH_EXTRA_SKILL = 200;

    private final String ENTITY_NAME = "JobOffer";

    public JobOfferValidator(IntegerRangeValidator integerRangeValidator, StringFieldValidator stringFieldValidator, ListValidator listValidator) {
        this.integerRangeValidator = integerRangeValidator;
        this.stringFieldValidator = stringFieldValidator;
        this.listValidator = listValidator;
    }

    public void validateDto(JobOfferDto jobOffer) {
        if (jobOffer == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        validateFields(jobOffer.getOfferName(), jobOffer.getWorkingTime(), jobOffer.getDuties(), jobOffer.getDescription(), jobOffer.getSalaryFrom(), jobOffer.getSalaryTo());
        listValidator.validateRequiredListMinMaxSize(jobOffer.getIndustries(), "industries", ENTITY_NAME, MIN_SIZE_INDUSTRIES, MAX_SIZE_INDUSTRIES, MAX_LENGTH_INDUSTRY);
        listValidator.validateRequiredListMinMaxSize(jobOffer.getLocalizations(), "localizations", ENTITY_NAME, MIN_SIZE_LOCALIZATIONS, MAX_SIZE_LOCALIZATIONS, MAX_LENGTH_LOCALIZATION);
        listValidator.validateRequiredList(jobOffer.getEmploymentForms(), "employmentForms", ENTITY_NAME, MAX_LENGTH_EMPLOYMENT_FORM);
        listValidator.validateRequiredList(jobOffer.getContractTypes(), "contractTypes", ENTITY_NAME, MAX_LENGTH_CONTRACT_TYPE);
        listValidator.validateRequiredList(jobOffer.getLevels(), "levels", ENTITY_NAME, MAX_LENGTH_LEVEL);
        listValidator.validateRequiredListMinMaxSize(jobOffer.getRequirements(), "requirements", ENTITY_NAME, MIN_SIZE_REQUIREMENTS, MAX_SIZE_REQUIREMENTS, MAX_LENGTH_REQUIREMENT);
        listValidator.validateListSizeAndElemLength(jobOffer.getExtraSkills(), "extraSkills", ENTITY_NAME, MAX_SIZE_EXTRA_SKILLS, MAX_LENGTH_EXTRA_SKILL);
    }

    public void validate(JobOffer jobOffer) {
        if (jobOffer == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        validateFields(jobOffer.getOfferName(), jobOffer.getWorkingTime(), jobOffer.getDuties(), jobOffer.getDescription(), jobOffer.getSalaryFrom(), jobOffer.getSalaryTo());
    }

    private void validateFields(String offerName, String workingTime, String duties, String description, Integer salaryFrom, Integer salaryTo) {
        stringFieldValidator.validateClassicStringRestrictedField(offerName, ENTITY_NAME, "offerName", MIN_OFFER_NAME_LENGTH, MAX_OFFER_NAME_LENGTH);
        stringFieldValidator.validateClassicStringRestrictedField(workingTime, ENTITY_NAME, "workingTime", MIN_WORKING_TIME_LENGTH, MAX_WORKING_TIME_LENGTH);
        stringFieldValidator.validateClassicStringRestrictedField(duties, ENTITY_NAME, "duties", MIN_DUTIES_LENGTH, MAX_DUTIES_LENGTH);
        stringFieldValidator.validateClassicStringRestrictedField(description, ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
        integerRangeValidator.validateSalaryRange(salaryFrom, salaryTo, ENTITY_NAME);
    }
}
