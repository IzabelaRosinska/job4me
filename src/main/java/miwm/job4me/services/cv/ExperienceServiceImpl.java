package miwm.job4me.services.cv;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Experience;
import miwm.job4me.repositories.cv.ExperienceRepository;
import miwm.job4me.validators.entity.cv.ExperienceValidator;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.web.mappers.cv.ExperienceMapper;
import miwm.job4me.web.model.cv.ExperienceDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExperienceServiceImpl implements ExperienceService {
    private final ExperienceRepository experienceRepository;
    private final ExperienceMapper experienceMapper;
    private final ExperienceValidator experienceValidator;
    private final IdValidator idValidator;
    private final String ENTITY_NAME = "Experience";

    public ExperienceServiceImpl(ExperienceRepository experienceRepository, ExperienceMapper experienceMapper, ExperienceValidator experienceValidator, IdValidator idValidator) {
        this.experienceRepository = experienceRepository;
        this.experienceMapper = experienceMapper;
        this.experienceValidator = experienceValidator;
        this.idValidator = idValidator;
    }

    @Override
    public boolean existsById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);

        return experienceRepository.existsById(id);
    }

    @Override
    public void strictExistsById(Long id) {
        if (!existsById(id)) {
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id));
        }
    }

    @Override
    public Set<ExperienceDto> findAll() {
        return experienceRepository
                .findAll()
                .stream()
                .map(experienceMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public ExperienceDto findById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        return experienceRepository
                .findById(id)
                .map(experienceMapper::toDto)
                .orElseThrow(() ->
                        new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id)));
    }

    @Override
    public ExperienceDto save(Experience experience) {
        experienceValidator.validate(experience);
        return experienceMapper.toDto(experienceRepository.save(experience));
    }

    @Override
    public void delete(Experience experience) {
        if (experience == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }
        strictExistsById(experience.getId());
        experienceRepository.delete(experience);
    }

    @Override
    public void deleteById(Long id) {
        strictExistsById(id);
        experienceRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ExperienceDto update(ExperienceDto experience) {
        experienceValidator.validateDto(experience);
        strictExistsById(experience.getId());
        return experienceMapper.toDto(experienceRepository.save(experienceMapper.toEntity(experience)));
    }

    @Override
    public List<ExperienceDto> findAllByEmployeeId(Long employeeId) {
        idValidator.validateLongId(employeeId, ENTITY_NAME);
        return experienceRepository
                .findAllByEmployeeId(employeeId)
                .stream()
                .map(experienceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllByEmployeeId(Long employeeId) {
        idValidator.validateLongId(employeeId, ENTITY_NAME);
        experienceRepository.deleteAllByEmployeeId(employeeId);
    }
}
