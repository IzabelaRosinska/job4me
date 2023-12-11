package miwm.job4me.services.offer.parameters;

import miwm.job4me.model.offer.parameters.ContractType;
import miwm.job4me.services.BaseDtoService;
import miwm.job4me.web.model.offer.ContractTypeDto;
import org.springframework.data.domain.Page;

public interface ContractTypeService extends BaseDtoService<ContractType, ContractTypeDto, Long> {
    Page<ContractTypeDto> findByNameContaining(int page, int size, String name);

    ContractTypeDto saveDto(ContractTypeDto contractTypeDto);

    boolean existsByName(String name);

    void strictExistsByName(String name);

    boolean existsById(Long id);

    void strictExistsById(Long id);

    ContractTypeDto update(Long id, ContractTypeDto contractType);

    ContractType findByName(String name);

}
