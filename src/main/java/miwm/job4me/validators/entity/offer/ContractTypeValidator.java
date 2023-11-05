package miwm.job4me.validators.entity.offer;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.ContractType;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.model.offer.ContractTypeDto;
import org.springframework.stereotype.Component;

@Component
public class ContractTypeValidator {
    private final StringFieldValidator stringFieldValidator;
    private final int MIN_NAME_LENGTH = 1;
    private final int MAX_NAME_LENGTH = 25;
    private final String ENTITY_NAME = "ContractType";

    public ContractTypeValidator(StringFieldValidator stringFieldValidator) {
        this.stringFieldValidator = stringFieldValidator;
    }

    public void validate(ContractType contractType) {
        if (contractType == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        stringFieldValidator.validateClassicStringRestrictedField(contractType.getName(), ENTITY_NAME, "name", MIN_NAME_LENGTH, MAX_NAME_LENGTH);
    }

    public void validateDto(ContractTypeDto contractType) {
        if (contractType == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        stringFieldValidator.validateClassicStringRestrictedField(contractType.getName(), ENTITY_NAME, "name", MIN_NAME_LENGTH, MAX_NAME_LENGTH);
    }
}
