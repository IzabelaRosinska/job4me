package miwm.job4me.services.cv;

import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Experience;
import miwm.job4me.repositories.cv.ExperienceRepository;
import miwm.job4me.validators.entity.IdValidator;
import miwm.job4me.validators.entity.cv.ExperienceValidator;
import miwm.job4me.web.mappers.cv.ExperienceMapper;
import miwm.job4me.web.model.cv.ExperienceDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    ExperienceRepository experienceRepository;
    ExperienceMapper experienceMapper;
    ExperienceValidator experienceValidator;
    IdValidator idValidator;
    private final String entityName = "Experience";

    public ExperienceServiceImpl(ExperienceRepository experienceRepository, ExperienceMapper experienceMapper, ExperienceValidator experienceValidator, IdValidator idValidator) {
        this.experienceRepository = experienceRepository;
        this.experienceMapper = experienceMapper;
        this.experienceValidator = experienceValidator;
        this.idValidator = idValidator;
    }

    private void existsById(Long id) {
        idValidator.validateLongId(id, entityName);
        experienceRepository.findById(id).orElseThrow(() ->
                new NoSuchElementFoundException(ExceptionMessages.elementNotFound(entityName, id)));
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
        idValidator.validateLongId(id, entityName);
        return experienceRepository
                .findById(id)
                .map(experienceMapper::toDto)
                .orElseThrow(() ->
                        new NoSuchElementFoundException(ExceptionMessages.elementNotFound(entityName, id)));
    }

    @Override
    public ExperienceDto save(Experience object) {
        experienceValidator.validate(object);
        return experienceMapper.toDto(experienceRepository.save(object));
    }

    @Transactional
    public ExperienceDto update(Experience object) {
        existsById(object.getId());
        experienceValidator.validate(object);
        return experienceMapper.toDto(experienceRepository.save(object));
    }

    @Override
    public void delete(Experience object) {
        existsById(object.getId());
        experienceRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        existsById(id);
        experienceRepository.deleteById(id);
    }

    public Set<ExperienceDto> findAllByEmployeeId(Long employeeId) {
        idValidator.validateLongId(employeeId, entityName);

        return experienceRepository
                .findAllByEmployeeId(employeeId)
                .stream()
                .map(experienceMapper::toDto)
                .collect(Collectors.toSet());
    }
}
