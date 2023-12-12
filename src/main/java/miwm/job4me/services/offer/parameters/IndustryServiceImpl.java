package miwm.job4me.services.offer.parameters;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.parameters.Industry;
import miwm.job4me.repositories.offer.IndustryRepository;
import miwm.job4me.validators.entity.offer.parameters.IndustryValidator;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.validators.pagination.PaginationValidator;
import miwm.job4me.web.mappers.offer.parameters.IndustryMapper;
import miwm.job4me.web.model.offer.IndustryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IndustryServiceImpl implements IndustryService {
    private final IndustryRepository industryRepository;
    private final IndustryMapper industryMapper;
    private final IndustryValidator industryValidator;
    private final IdValidator idValidator;
    private final PaginationValidator paginationValidator;
    private final String ENTITY_NAME = "Industry";

    public IndustryServiceImpl(IndustryRepository industryRepository, IndustryMapper industryMapper, IndustryValidator industryValidator, IdValidator idValidator, PaginationValidator paginationValidator) {
        this.industryRepository = industryRepository;
        this.industryMapper = industryMapper;
        this.industryValidator = industryValidator;
        this.idValidator = idValidator;
        this.paginationValidator = paginationValidator;
    }

    @Override
    public Set<IndustryDto> findAll() {
        return industryRepository
                .findAll()
                .stream()
                .map(industryMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public IndustryDto findById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        return industryRepository
                .findById(id)
                .map(industryMapper::toDto)
                .orElseThrow(() -> new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id)));
    }

    @Override
    public IndustryDto save(Industry industry) {
        idValidator.validateNoIdForCreate(industry.getId(), ENTITY_NAME);
        industryValidator.validate(industry);

        if (existsByName(industry.getName())) {
            throw new InvalidArgumentException(ExceptionMessages.elementAlreadyExists(ENTITY_NAME, "name", industry.getName()));
        }

        return industryMapper.toDto(industryRepository.save(industry));
    }

    @Override
    public Page<IndustryDto> findByNameContaining(int page, int size, String name) {
        paginationValidator.validatePagination(page, size);

        return industryRepository
                .findByNameContaining(PageRequest.of(page, size), name)
                .map(industryMapper::toDto);
    }

    @Override
    public IndustryDto saveDto(IndustryDto industry) {
        idValidator.validateNoIdForCreate(industry.getId(), ENTITY_NAME);
        industryValidator.validateDto(industry);

        if (existsByName(industry.getName())) {
            throw new InvalidArgumentException(ExceptionMessages.elementAlreadyExists(ENTITY_NAME, "name", industry.getName()));
        }

        return industryMapper.toDto(industryRepository.save(industryMapper.toEntity(industry)));
    }

    @Override
    public void delete(Industry industry) {
        if (industry == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        strictExistsById(industry.getId());
        industryRepository.delete(industry);
    }

    @Override
    public void deleteById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        strictExistsById(id);
        industryRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return industryRepository.existsByName(name);
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
        return industryRepository.existsById(id);
    }

    @Override
    public void strictExistsById(Long id) {
        if (!existsById(id)) {
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id));
        }
    }

    @Override
    public IndustryDto update(Long id, IndustryDto industry) {
        strictExistsById(industry.getId());
        industry.setId(id);
        industryValidator.validateDto(industry);
        return industryMapper.toDto(industryRepository.save(industryMapper.toEntity(industry)));
    }

    @Override
    public Industry findByName(String name) {
        Industry industry = industryRepository.findByName(name);

        if (industry == null) {
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFoundByName(ENTITY_NAME, name));
        }

        return industry;
    }
}
