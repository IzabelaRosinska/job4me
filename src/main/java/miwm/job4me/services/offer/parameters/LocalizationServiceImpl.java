package miwm.job4me.services.offer.parameters;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.parameters.Localization;
import miwm.job4me.repositories.offer.LocalizationRepository;
import miwm.job4me.validators.entity.offer.parameters.LocalizationValidator;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.validators.pagination.PaginationValidator;
import miwm.job4me.web.mappers.offer.parameters.LocalizationMapper;
import miwm.job4me.web.model.offer.LocalizationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LocalizationServiceImpl implements LocalizationService {
    private final LocalizationRepository localizationRepository;
    private final LocalizationMapper localizationMapper;
    private final LocalizationValidator localizationValidator;
    private final IdValidator idValidator;
    private final StringFieldValidator stringFieldValidator;
    private final PaginationValidator paginationValidator;
    private final String ENTITY_CITY = "Localization";

    public LocalizationServiceImpl(LocalizationRepository localizationRepository, LocalizationMapper localizationMapper, LocalizationValidator localizationValidator, IdValidator idValidator, StringFieldValidator stringFieldValidator, PaginationValidator paginationValidator) {
        this.localizationRepository = localizationRepository;
        this.localizationMapper = localizationMapper;
        this.localizationValidator = localizationValidator;
        this.idValidator = idValidator;
        this.stringFieldValidator = stringFieldValidator;
        this.paginationValidator = paginationValidator;
    }

    @Override
    public Set<LocalizationDto> findAll() {
        return localizationRepository
                .findAll()
                .stream()
                .map(localizationMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public LocalizationDto findById(Long id) {
        idValidator.validateLongId(id, ENTITY_CITY);
        return localizationRepository
                .findById(id)
                .map(localizationMapper::toDto)
                .orElseThrow(() -> new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_CITY, id)));
    }

    @Override
    public LocalizationDto save(Localization Localization) {
        localizationValidator.validate(Localization);
        idValidator.validateNoIdForCreate(Localization.getId(), ENTITY_CITY);

        if (existsByCity(Localization.getCity())) {
            throw new InvalidArgumentException(ExceptionMessages.elementAlreadyExists(ENTITY_CITY, "city", Localization.getCity()));
        }

        return localizationMapper.toDto(localizationRepository.save(Localization));
    }

    @Override
    public Page<LocalizationDto> findByCityContaining(int page, int size, String city) {
        paginationValidator.validatePagination(page, size);

        return localizationRepository
                .findByCityContaining(PageRequest.of(page, size), city)
                .map(localizationMapper::toDto);
    }

    @Override
    public LocalizationDto saveDto(LocalizationDto localization) {
        idValidator.validateNoIdForCreate(localization.getId(), ENTITY_CITY);
        localizationValidator.validateDto(localization);

        localization.setId(null);

        if (existsByCity(localization.getCity())) {
            throw new InvalidArgumentException(ExceptionMessages.elementAlreadyExists(ENTITY_CITY, "city", localization.getCity()));
        }

        return localizationMapper.toDto(localizationRepository.save(localizationMapper.toEntity(localization)));
    }

    @Override
    public void delete(Localization Localization) {
        if (Localization == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_CITY));
        }

        strictExistsById(Localization.getId());
        localizationRepository.delete(Localization);
    }

    @Override
    public void deleteById(Long id) {
        idValidator.validateLongId(id, ENTITY_CITY);
        strictExistsById(id);
        localizationRepository.deleteById(id);
    }

    @Override
    public boolean existsByCity(String city) {
        stringFieldValidator.validateNotNullNotEmpty(city, ENTITY_CITY, "city");
        return localizationRepository.existsByCity(city);
    }

    @Override
    public void strictExistsByCity(String city) {
        if (!existsByCity(city)) {
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFoundByName(ENTITY_CITY, city));
        }
    }

    @Override
    public boolean existsById(Long id) {
        idValidator.validateLongId(id, ENTITY_CITY);
        return localizationRepository.existsById(id);
    }

    @Override
    public void strictExistsById(Long id) {
        if (!existsById(id)) {
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_CITY, id));
        }
    }

    @Override
    public LocalizationDto update(Long id, LocalizationDto localization) {
        strictExistsById(id);
        localizationValidator.validateDto(localization);
        localization.setId(id);
        return localizationMapper.toDto(localizationRepository.save(localizationMapper.toEntity(localization)));
    }

    @Override
    public Localization findByCity(String city) {
        stringFieldValidator.validateNotNullNotEmpty(city, ENTITY_CITY, "city");
        Localization localization = localizationRepository.findByCity(city);

        if (localization == null) {
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFoundByName(ENTITY_CITY, city));
        }

        return localization;
    }

    @Override
    public Localization findByCityOrCreate(String city) {
        Localization localization = localizationRepository.findByCity(city);

        if (localization == null) {
            localization = new Localization();
            localization.setCity(city);
            localization = localizationRepository.save(localization);
        }

        return localization;
    }
}
