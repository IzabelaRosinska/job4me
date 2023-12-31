package miwm.job4me.services.offer.parameters;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.parameters.ContractType;
import miwm.job4me.repositories.offer.ContractTypeRepository;
import miwm.job4me.validators.entity.offer.parameters.ContractTypeValidator;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.validators.pagination.PaginationValidator;
import miwm.job4me.web.mappers.offer.parameters.ContractTypeMapper;
import miwm.job4me.web.model.offer.ContractTypeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ContractTypeServiceImpl implements ContractTypeService {
    private final ContractTypeRepository contractTypeRepository;
    private final ContractTypeMapper contractTypeMapper;
    private final ContractTypeValidator contractTypeValidator;
    private final IdValidator idValidator;
    private final StringFieldValidator stringFieldValidator;
    private final PaginationValidator paginationValidator;
    private final String ENTITY_NAME = "ContractType";

    public ContractTypeServiceImpl(ContractTypeRepository contractTypeRepository, ContractTypeMapper contractTypeMapper, ContractTypeValidator contractTypeValidator, IdValidator idValidator, StringFieldValidator stringFieldValidator, PaginationValidator paginationValidator) {
        this.contractTypeRepository = contractTypeRepository;
        this.contractTypeMapper = contractTypeMapper;
        this.contractTypeValidator = contractTypeValidator;
        this.idValidator = idValidator;
        this.stringFieldValidator = stringFieldValidator;
        this.paginationValidator = paginationValidator;
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
    public ContractTypeDto findById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        return contractTypeRepository
                .findById(id)
                .map(contractTypeMapper::toDto)
                .orElseThrow(() -> new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id)));
    }

    @Override
    public ContractTypeDto save(ContractType contractType) {
        contractTypeValidator.validate(contractType);
        idValidator.validateNoIdForCreate(contractType.getId(), ENTITY_NAME);

        if (existsByName(contractType.getName())) {
            throw new InvalidArgumentException(ExceptionMessages.elementAlreadyExists(ENTITY_NAME, "name", contractType.getName()));
        }

        return contractTypeMapper.toDto(contractTypeRepository.save(contractType));
    }

    @Override
    public ContractTypeDto saveDto(ContractTypeDto contractTypeDto) {
        idValidator.validateNoIdForCreate(contractTypeDto.getId(), ENTITY_NAME);
        contractTypeValidator.validateDto(contractTypeDto);

        if (existsByName(contractTypeDto.getName())) {
            throw new InvalidArgumentException(ExceptionMessages.elementAlreadyExists(ENTITY_NAME, "name", contractTypeDto.getName()));
        }

        return contractTypeMapper.toDto(contractTypeRepository.save(contractTypeMapper.toEntity(contractTypeDto)));
    }

    @Override
    public void delete(ContractType contractType) {
        if (contractType == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        strictExistsById(contractType.getId());
        contractTypeRepository.delete(contractType);
    }

    @Override
    public void deleteById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        strictExistsById(id);
        contractTypeRepository.deleteById(id);
    }

    @Override
    public Page<ContractTypeDto> findByNameContaining(int page, int size, String name) {
        paginationValidator.validatePagination(page, size);

        return contractTypeRepository
                .findByNameContaining(PageRequest.of(page, size), name)
                .map(contractTypeMapper::toDto);
    }

    @Override
    public boolean existsByName(String name) {
        stringFieldValidator.validateNotNullNotEmpty(name, ENTITY_NAME, "name");
        return contractTypeRepository.existsByName(name);
    }

    @Override
    public void strictExistsByName(String name) {
        if (!existsByName(name)) {
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFoundByName(ENTITY_NAME, name));
        }
    }

    @Override
    public boolean existsById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        return contractTypeRepository.existsById(id);
    }

    @Override
    public void strictExistsById(Long id) {
        if (!existsById(id)) {
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id));
        }
    }

    @Override
    public ContractTypeDto update(Long id, ContractTypeDto contractType) {
        strictExistsById(id);
        contractTypeValidator.validateDto(contractType);
        contractType.setId(id);
        return contractTypeMapper.toDto(contractTypeRepository.save(contractTypeMapper.toEntity(contractType)));
    }

    @Override
    public ContractType findByName(String name) {
        stringFieldValidator.validateNotNullNotEmpty(name, ENTITY_NAME, "name");
        ContractType contractType = contractTypeRepository.findByName(name);

        if (contractType == null) {
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, "name", name));
        }

        return contractType;
    }
}
