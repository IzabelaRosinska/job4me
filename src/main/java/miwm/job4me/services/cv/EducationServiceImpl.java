package miwm.job4me.services.cv;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Education;
import miwm.job4me.repositories.cv.EducationRepository;
import miwm.job4me.validators.entity.cv.EducationValidator;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.web.mappers.cv.EducationMapper;
import miwm.job4me.web.model.cv.EducationDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EducationServiceImpl implements EducationService {
    private final EducationRepository educationRepository;
    private final EducationMapper educationMapper;
    private final EducationValidator educationValidator;
    private final IdValidator idValidator;
    private final String ENTITY_NAME = "Education";

    public EducationServiceImpl(EducationRepository educationRepository, EducationMapper educationMapper, EducationValidator educationValidator, IdValidator idValidator) {
        this.educationRepository = educationRepository;
        this.educationMapper = educationMapper;
        this.educationValidator = educationValidator;
        this.idValidator = idValidator;
    }

    @Override
    public boolean existsById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);

        return educationRepository.existsById(id);
    }

    @Override
    public void strictExistsById(Long id) {
        if (!existsById(id)) {
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id));
        }
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
        idValidator.validateLongId(id, ENTITY_NAME);
        return educationRepository
                .findById(id)
                .map(educationMapper::toDto)
                .orElseThrow(() ->
                        new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id)));
    }

    @Override
    public EducationDto save(Education education) {
        educationValidator.validate(education);
        return educationMapper.toDto(educationRepository.save(education));
    }

    @Override
    @Transactional
    public void delete(Education education) {
        if (education == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }
        strictExistsById(education.getId());
        educationRepository.delete(education);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        strictExistsById(id);
        educationRepository.deleteById(id);
    }

    @Override
    @Transactional
    public EducationDto update(EducationDto education) {
        educationValidator.validateDto(education);
        strictExistsById(education.getId());
        return educationMapper.toDto(educationRepository.save(educationMapper.toEntity(education)));
    }

    @Override
    public Set<EducationDto> findAllByEmployeeId(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        return educationRepository
                .findAllByEmployeeId(id)
                .stream()
                .map(educationMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteAllByEmployeeId(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        educationRepository.deleteAllByEmployeeId(id);
    }
}
