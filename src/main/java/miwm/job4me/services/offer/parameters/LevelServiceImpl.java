package miwm.job4me.services.offer.parameters;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.parameters.Level;
import miwm.job4me.repositories.offer.LevelRepository;
import miwm.job4me.validators.entity.offer.parameters.LevelValidator;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.validators.pagination.PaginationValidator;
import miwm.job4me.web.mappers.offer.parameters.LevelMapper;
import miwm.job4me.web.model.offer.LevelDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LevelServiceImpl implements LevelService {
    private final LevelRepository levelRepository;
    private final LevelMapper levelMapper;
    private final LevelValidator levelValidator;
    private final IdValidator idValidator;
    private final PaginationValidator paginationValidator;
    private final String ENTITY_NAME = "Level";

    public LevelServiceImpl(LevelRepository levelRepository, LevelMapper levelMapper, LevelValidator levelValidator, IdValidator idValidator, PaginationValidator paginationValidator) {
        this.levelRepository = levelRepository;
        this.levelMapper = levelMapper;
        this.levelValidator = levelValidator;
        this.idValidator = idValidator;
        this.paginationValidator = paginationValidator;
    }

    @Override
    public Set<LevelDto> findAll() {
        return levelRepository
                .findAll()
                .stream()
                .map(levelMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public LevelDto findById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        return levelRepository
                .findById(id)
                .map(levelMapper::toDto)
                .orElseThrow(() -> new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id)));
    }

    @Override
    public LevelDto save(Level level) {
        idValidator.validateNoIdForCreate(level.getId(), ENTITY_NAME);
        levelValidator.validate(level);

        if (existsByName(level.getName())) {
            throw new InvalidArgumentException(ExceptionMessages.elementAlreadyExists(ENTITY_NAME, "name", level.getName()));
        }

        return levelMapper.toDto(levelRepository.save(level));
    }

    @Override
    public Page<LevelDto> findByNameContaining(int page, int size, String name) {
        paginationValidator.validatePagination(page, size);

        return levelRepository
                .findByNameContaining(PageRequest.of(page, size), name)
                .map(levelMapper::toDto);
    }

    @Override
    public LevelDto saveDto(LevelDto level) {
        idValidator.validateNoIdForCreate(level.getId(), ENTITY_NAME);
        levelValidator.validateDto(level);

        if (existsByName(level.getName())) {
            throw new InvalidArgumentException(ExceptionMessages.elementAlreadyExists(ENTITY_NAME, "name", level.getName()));
        }

        return levelMapper.toDto(levelRepository.save(levelMapper.toEntity(level)));
    }

    @Override
    public void delete(Level level) {
        if (level == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        strictExistsById(level.getId());
        levelRepository.delete(level);
    }

    @Override
    public void deleteById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        strictExistsById(id);
        levelRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return levelRepository.existsByName(name);
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
        return levelRepository.existsById(id);
    }

    @Override
    public void strictExistsById(Long id) {
        if (!existsById(id)) {
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id));
        }
    }

    @Override
    public LevelDto update(Long id, LevelDto level) {
        strictExistsById(level.getId());
        level.setId(id);
        levelValidator.validateDto(level);
        return levelMapper.toDto(levelRepository.save(levelMapper.toEntity(level)));
    }

    @Override
    public Level findByName(String name) {
        Level level = levelRepository.findByName(name);

        if (level == null) {
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFoundByName(ENTITY_NAME, name));
        }

        return level;
    }
}
