package miwm.job4me.services.offer.description;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.ExtraSkill;
import miwm.job4me.repositories.offer.ExtraSkillRepository;
import miwm.job4me.validators.entity.offer.ExtraSkillValidator;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.web.mappers.offer.ExtraSkillMapper;
import miwm.job4me.web.model.offer.ExtraSkillDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExtraSkillServiceImpl implements ExtraSkillService {
    private final ExtraSkillRepository extraSkillRepository;
    private final ExtraSkillMapper extraSkillMapper;
    private final ExtraSkillValidator extraSkillValidator;
    private final IdValidator idValidator;
    private final String ENTITY_NAME = "ExtraSkill";

    public ExtraSkillServiceImpl(ExtraSkillRepository extraSkillRepository, ExtraSkillMapper extraSkillMapper, ExtraSkillValidator extraSkillValidator, IdValidator idValidator) {
        this.extraSkillRepository = extraSkillRepository;
        this.extraSkillMapper = extraSkillMapper;
        this.extraSkillValidator = extraSkillValidator;
        this.idValidator = idValidator;
    }

    @Override
    public boolean existsById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);

        return extraSkillRepository.existsById(id);
    }

    @Override
    public void strictExistsById(Long id) {
        if (!existsById(id)) {
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id));
        }
    }

    @Override
    public Set<ExtraSkillDto> findAll() {
        return extraSkillRepository
                .findAll()
                .stream()
                .map(extraSkillMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public ExtraSkillDto findById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        return extraSkillRepository
                .findById(id)
                .map(extraSkillMapper::toDto)
                .orElseThrow(() ->
                        new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id)));
    }

    @Override
    public ExtraSkillDto save(ExtraSkill extraSkill) {
        idValidator.validateNoIdForCreate(extraSkill.getId(), ENTITY_NAME);
        extraSkillValidator.validate(extraSkill);
        return extraSkillMapper.toDto(extraSkillRepository.save(extraSkill));
    }

    @Override
    @Transactional
    public void delete(ExtraSkill extraSkill) {
        if (extraSkill == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }
        strictExistsById(extraSkill.getId());
        extraSkillRepository.delete(extraSkill);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        strictExistsById(id);
        extraSkillRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ExtraSkillDto update(Long id, ExtraSkillDto extraSkill) {
        strictExistsById(id);
        extraSkill.setId(id);
        extraSkillValidator.validateDto(extraSkill);
        return extraSkillMapper.toDto(extraSkillRepository.save(extraSkillMapper.toEntity(extraSkill)));
    }

    @Override
    public List<ExtraSkillDto> findAllByJobOfferId(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        return extraSkillRepository
                .findAllByJobOfferId(id)
                .stream()
                .map(extraSkillMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllByJobOfferId(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        extraSkillRepository.deleteAllByJobOfferId(id);
    }
}
