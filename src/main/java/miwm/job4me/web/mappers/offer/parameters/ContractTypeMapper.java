package miwm.job4me.web.mappers.offer.parameters;

import miwm.job4me.model.offer.parameters.ContractType;
import miwm.job4me.web.model.offer.ContractTypeDto;
import org.springframework.stereotype.Component;

@Component
public class ContractTypeMapper {
    public ContractTypeDto toDto(ContractType contractType) {
        ContractTypeDto contractTypeDto = new ContractTypeDto();
        contractTypeDto.setId(contractType.getId());
        contractTypeDto.setName(contractType.getName());
        return contractTypeDto;
    }

    public ContractType toEntity(ContractTypeDto contractTypeDto) {
        ContractType contractType = new ContractType();
        contractType.setId(contractTypeDto.getId());
        contractType.setName(contractTypeDto.getName());
        return contractType;
    }
}
