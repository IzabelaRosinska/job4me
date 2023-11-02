package miwm.job4me.services.cv;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Skill;
import miwm.job4me.repositories.cv.SkillRepository;
import miwm.job4me.validators.entity.cv.SkillValidator;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.web.mappers.cv.SkillMapper;
import miwm.job4me.web.model.cv.SkillDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class SkillServiceImpl implements SkillService {
    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;
    private final SkillValidator skillValidator;
    private final IdValidator idValidator;
    private final String ENTITY_NAME = "Skill";

    public SkillServiceImpl(SkillRepository skillRepository, SkillMapper skillMapper, SkillValidator skillValidator, IdValidator idValidator) {
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
        this.skillValidator = skillValidator;
        this.idValidator = idValidator;
    }

    @Override
    public boolean existsById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);

        return skillRepository.existsById(id);
    }

    @Override
    public void strictExistsById(Long id) {
        if (!existsById(id)) {
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id));
        }
    }

    @Override
    public Set<SkillDto> findAll() {
        return skillRepository
                .findAll()
                .stream()
                .map(skillMapper::toDto)
                .collect(java.util.stream.Collectors.toSet());
    }

    @Override
    public SkillDto findById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        return skillRepository
                .findById(id)
                .map(skillMapper::toDto)
                .orElseThrow(() ->
                        new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id)));
    }

    @Override
    public SkillDto save(Skill skill) {
        skillValidator.validate(skill);
        return skillMapper.toDto(skillRepository.save(skill));
    }

    @Override
    public void delete(Skill skill) {
        if (skill == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }
        strictExistsById(skill.getId());
        skillRepository.delete(skill);
    }

    @Override
    public void deleteById(Long id) {
        strictExistsById(id);
        skillRepository.deleteById(id);
    }

    @Override
    @Transactional
    public SkillDto update(SkillDto skill) {
        skillValidator.validateDto(skill);
        strictExistsById(skill.getId());
        return skillMapper.toDto(skillRepository.save(skillMapper.toEntity(skill)));
    }

    @Override
    public Set<SkillDto> findAllByEmployeeId(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        return skillRepository
                .findAllByEmployeeId(id)
                .stream()
                .map(skillMapper::toDto)
                .collect(java.util.stream.Collectors.toSet());
    }

    @Override
    public void deleteAllByEmployeeId(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        skillRepository.deleteAllByEmployeeId(id);
    }
}
