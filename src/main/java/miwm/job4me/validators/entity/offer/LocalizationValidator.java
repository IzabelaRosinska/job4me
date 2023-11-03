package miwm.job4me.validators.entity.offer;

import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.Localization;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.model.offer.LocalizationDto;
import org.springframework.stereotype.Component;

@Component
public class LocalizationValidator {
    private final IdValidator idValidator;
    private final StringFieldValidator stringFieldValidator;
    private final int MIN_CITY_LENGTH = 1;
    private final int MAX_CITY_LENGTH = 50;
    private final String ENTITY_NAME = "Localization";

    public LocalizationValidator(IdValidator idValidator, StringFieldValidator stringFieldValidator) {
        this.idValidator = idValidator;
        this.stringFieldValidator = stringFieldValidator;
    }

    public void validate(Localization localization) {
        if (localization == null) {
            throw new IllegalArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        idValidator.validateLongId(localization.getId(), ENTITY_NAME);
        stringFieldValidator.validateClassicStringRestrictedField(localization.getCity(), ENTITY_NAME, "city", MIN_CITY_LENGTH, MAX_CITY_LENGTH);
    }

    public void validateDto(LocalizationDto localization) {
        if (localization == null) {
            throw new IllegalArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        idValidator.validateLongId(localization.getId(), ENTITY_NAME);
        stringFieldValidator.validateClassicStringRestrictedField(localization.getCity(), ENTITY_NAME, "city", MIN_CITY_LENGTH, MAX_CITY_LENGTH);
    }
}
