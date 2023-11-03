package miwm.job4me.services.offer;

import miwm.job4me.model.offer.ContractType;
import miwm.job4me.repositories.offer.ContractTypeRepository;
import miwm.job4me.validators.entity.offer.ContractTypeValidator;
import miwm.job4me.web.mappers.offer.ContractTypeMapper;
import miwm.job4me.web.model.offer.ContractTypeDto;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ContractTypeServiceImpl implements ContractTypeService {
    private final ContractTypeRepository contractTypeRepository;
    private final ContractTypeMapper contractTypeMapper;
    private final ContractTypeValidator contractTypeValidator;
    private final String ENTITY_NAME = "ContractType";

    public ContractTypeServiceImpl(ContractTypeRepository contractTypeRepository, ContractTypeMapper contractTypeMapper, ContractTypeValidator contractTypeValidator) {
        this.contractTypeRepository = contractTypeRepository;
        this.contractTypeMapper = contractTypeMapper;
        this.contractTypeValidator = contractTypeValidator;
    }

    @Override
    public Set<ContractTypeDto> findAll() {
        return contractTypeRepository
                .findAll()
                .stream()
                .map(contractTypeMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public ContractTypeDto findById(Long aLong) {
        return null;
    }

    @Override
    public ContractTypeDto save(ContractType object) {
        return null;
    }

    @Override
    public void delete(ContractType object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}
