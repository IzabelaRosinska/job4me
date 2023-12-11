package miwm.job4me.validators.entity.offer.parameters;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.parameters.Localization;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.model.offer.LocalizationDto;
import org.springframework.stereotype.Component;

@Component
public class LocalizationValidator {
    private final StringFieldValidator stringFieldValidator;
    private final int MIN_CITY_LENGTH = 1;
    private final int MAX_CITY_LENGTH = 50;
    private final String ENTITY_NAME = "Localization";

    public LocalizationValidator(StringFieldValidator stringFieldValidator) {
        this.stringFieldValidator = stringFieldValidator;
    }

    public void validate(Localization localization) {
        if (localization == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        stringFieldValidator.validateClassicStringRestrictedField(localization.getCity(), ENTITY_NAME, "city", MIN_CITY_LENGTH, MAX_CITY_LENGTH);
    }

    public void validateDto(LocalizationDto localization) {
        if (localization == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        stringFieldValidator.validateClassicStringRestrictedField(localization.getCity(), ENTITY_NAME, "city", MIN_CITY_LENGTH, MAX_CITY_LENGTH);
    }
}
