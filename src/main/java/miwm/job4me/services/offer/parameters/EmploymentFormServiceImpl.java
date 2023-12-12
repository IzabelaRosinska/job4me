package miwm.job4me.services.offer.parameters;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.parameters.EmploymentForm;
import miwm.job4me.repositories.offer.EmploymentFormRepository;
import miwm.job4me.validators.entity.offer.parameters.EmploymentFormValidator;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.validators.pagination.PaginationValidator;
import miwm.job4me.web.mappers.offer.parameters.EmploymentFormMapper;
import miwm.job4me.web.model.offer.EmploymentFormDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmploymentFormServiceImpl implements EmploymentFormService {
    private final EmploymentFormRepository employmentFormRepository;
    private final EmploymentFormMapper employmentFormMapper;
    private final EmploymentFormValidator employmentFormValidator;
    private final IdValidator idValidator;
    private final PaginationValidator paginationValidator;
    private final String ENTITY_NAME = "EmploymentForm";

    public EmploymentFormServiceImpl(EmploymentFormRepository employmentFormRepository, EmploymentFormMapper employmentFormMapper, EmploymentFormValidator employmentFormValidator, IdValidator idValidator, PaginationValidator paginationValidator) {
        this.employmentFormRepository = employmentFormRepository;
        this.employmentFormMapper = employmentFormMapper;
        this.employmentFormValidator = employmentFormValidator;
        this.idValidator = idValidator;
        this.paginationValidator = paginationValidator;
    }

    @Override
    public Set<EmploymentFormDto> findAll() {
        return employmentFormRepository
                .findAll()
                .stream()
                .map(employmentFormMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public EmploymentFormDto findById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        return employmentFormRepository
                .findById(id)
                .map(employmentFormMapper::toDto)
                .orElseThrow(() -> new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id)));
    }

    @Override
    public EmploymentFormDto save(EmploymentForm employmentForm) {
        idValidator.validateNoIdForCreate(employmentForm.getId(), ENTITY_NAME);
        employmentFormValidator.validate(employmentForm);

        if (existsByName(employmentForm.getName())) {
            throw new InvalidArgumentException(ExceptionMessages.elementAlreadyExists(ENTITY_NAME, "name", employmentForm.getName()));
        }

        return employmentFormMapper.toDto(employmentFormRepository.save(employmentForm));
    }

    @Override
    public Page<EmploymentFormDto> findByNameContaining(int page, int size, String name) {
        paginationValidator.validatePagination(page, size);

        return employmentFormRepository
                .findByNameContaining(PageRequest.of(page, size), name)
                .map(employmentFormMapper::toDto);
    }

    @Override
    public EmploymentFormDto saveDto(EmploymentFormDto employmentForm) {
        idValidator.validateNoIdForCreate(employmentForm.getId(), ENTITY_NAME);
        employmentFormValidator.validateDto(employmentForm);

        if (existsByName(employmentForm.getName())) {
            throw new InvalidArgumentException(ExceptionMessages.elementAlreadyExists(ENTITY_NAME, "name", employmentForm.getName()));
        }

        return employmentFormMapper.toDto(employmentFormRepository.save(employmentFormMapper.toEntity(employmentForm)));
    }


    @Override
    public void delete(EmploymentForm employmentForm) {
        if (employmentForm == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        strictExistsById(employmentForm.getId());
        employmentFormRepository.delete(employmentForm);
    }

    @Override
    public void deleteById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        strictExistsById(id);
        employmentFormRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return employmentFormRepository.existsByName(name);
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
        return employmentFormRepository.existsById(id);
    }

    @Override
    public void strictExistsById(Long id) {
        if (!existsById(id)) {
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id));
        }
    }

    @Override
    public EmploymentFormDto update(Long id, EmploymentFormDto employmentForm) {
        strictExistsById(id);
        employmentForm.setId(id);
        employmentFormValidator.validateDto(employmentForm);
        return employmentFormMapper.toDto(employmentFormRepository.save(employmentFormMapper.toEntity(employmentForm)));
    }

    @Override
    public EmploymentForm findByName(String name) {
        EmploymentForm employmentForm = employmentFormRepository.findByName(name);

        if (employmentForm == null) {
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFoundByName(ENTITY_NAME, name));
        }

        return employmentForm;
    }
}
