package miwm.job4me.validators.entity.offer;

import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.Industry;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.model.offer.IndustryDto;
import org.springframework.stereotype.Component;

@Component
public class IndustryValidator {
    private final IdValidator idValidator;
    private final StringFieldValidator stringFieldValidator;
    private final int MIN_NAME_LENGTH = 1;
    private final int MAX_NAME_LENGTH = 100;
    private final String ENTITY_NAME = "Industry";

    public IndustryValidator(IdValidator idValidator, StringFieldValidator stringFieldValidator) {
        this.idValidator = idValidator;
        this.stringFieldValidator = stringFieldValidator;
    }

    public void validate(Industry industry) {
        if (industry == null) {
            throw new IllegalArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        idValidator.validateLongId(industry.getId(), ENTITY_NAME);
        stringFieldValidator.validateClassicStringRestrictedField(industry.getName(), ENTITY_NAME, "name", MIN_NAME_LENGTH, MAX_NAME_LENGTH);
    }

    public void validateDto(IndustryDto industry) {
        if (industry == null) {
            throw new IllegalArgumentException("Industry cannot be null");
        }

        idValidator.validateLongId(industry.getId(), ENTITY_NAME);
        stringFieldValidator.validateClassicStringRestrictedField(industry.getName(), ENTITY_NAME, "name", MIN_NAME_LENGTH, MAX_NAME_LENGTH);
    }
}
