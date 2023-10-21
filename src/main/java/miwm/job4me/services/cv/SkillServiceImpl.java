package miwm.job4me.services.cv;

import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Skill;
import miwm.job4me.repositories.cv.SkillRepository;
import miwm.job4me.validators.entity.IdValidator;
import miwm.job4me.validators.entity.cv.SkillValidator;
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

    private void existsById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        skillRepository.findById(id).orElseThrow(() ->
                new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id)));
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
    public SkillDto save(Skill object) {
        skillValidator.validate(object);

        return skillMapper.toDto(skillRepository.save(object));
    }

    @Override
    public void delete(Skill object) {
        existsById(object.getId());
        skillRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        existsById(id);
        skillRepository.deleteById(id);
    }

    @Override
    @Transactional
    public SkillDto update(Skill object) {
        existsById(object.getId());
        skillValidator.validate(object);

        return skillMapper.toDto(skillRepository.save(object));
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
