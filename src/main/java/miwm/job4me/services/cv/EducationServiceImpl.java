package miwm.job4me.services.cv;

import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Education;
import miwm.job4me.repositories.cv.EducationRepository;
import miwm.job4me.validators.entity.IdValidator;
import miwm.job4me.validators.entity.cv.EducationValidator;
import miwm.job4me.web.mappers.cv.EducationMapper;
import miwm.job4me.web.model.cv.EducationDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EducationServiceImpl implements EducationService {
    EducationRepository educationRepository;
    EducationMapper educationMapper;
    EducationValidator educationValidator;
    IdValidator idValidator;
    private final String entityName = "Education";

    public EducationServiceImpl(EducationRepository educationRepository, EducationMapper educationMapper, EducationValidator educationValidator, IdValidator idValidator) {
        this.educationRepository = educationRepository;
        this.educationMapper = educationMapper;
        this.educationValidator = educationValidator;
        this.idValidator = idValidator;
    }

    private void existsById(Long id) {
        idValidator.validateLongId(id, entityName);
        educationRepository.findById(id).orElseThrow(() ->
                new NoSuchElementFoundException(ExceptionMessages.elementNotFound(entityName, id)));
    }

    @Override
    public Set<EducationDto> findAll() {
        return educationRepository
                .findAll()
                .stream()
                .map(educationMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public EducationDto findById(Long id) {
        idValidator.validateLongId(id, entityName);
        return educationRepository
                .findById(id)
                .map(educationMapper::toDto)
                .orElseThrow(() ->
                        new NoSuchElementFoundException(ExceptionMessages.elementNotFound(entityName, id)));
    }

    @Override
    public EducationDto save(Education object) {
        educationValidator.validate(object);
        return educationMapper.toDto(educationRepository.save(object));
    }

    @Override
    @Transactional
    public void delete(Education object) {
        existsById(object.getId());
        educationRepository.delete(object);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        existsById(id);
        educationRepository.deleteById(id);
    }

    @Transactional
    public EducationDto update(EducationDto education) {
        existsById(education.getId());
        educationValidator.validateDto(education);
        return educationMapper.toDto(educationRepository.save(educationMapper.toEntity(education)));
    }

    public Set<EducationDto> findAllByEmployeeId(Long id) {
        idValidator.validateLongId(id, entityName);
        return educationRepository
                .findAllByEmployeeId(id)
                .stream()
                .map(educationMapper::toDto)
                .collect(Collectors.toSet());
    }

    public void deleteAllByEmployeeId(Long id) {
        idValidator.validateLongId(id, entityName);
        educationRepository.deleteAllByEmployeeId(id);
    }
}