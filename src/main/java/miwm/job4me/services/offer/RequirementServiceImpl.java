package miwm.job4me.services.offer;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.Requirement;
import miwm.job4me.repositories.offer.RequirementRepository;
import miwm.job4me.validators.entity.offer.RequirementValidator;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.web.mappers.offer.RequirementMapper;
import miwm.job4me.web.model.offer.RequirementDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RequirementServiceImpl implements RequirementService {
    private final RequirementRepository requirementRepository;
    private final RequirementMapper requirementMapper;
    private final RequirementValidator requirementValidator;
    private final IdValidator idValidator;
    private final String ENTITY_NAME = "Requirement";

    public RequirementServiceImpl(RequirementRepository requirementRepository, RequirementMapper requirementMapper, RequirementValidator requirementValidator, IdValidator idValidator) {
        this.requirementRepository = requirementRepository;
        this.requirementMapper = requirementMapper;
        this.requirementValidator = requirementValidator;
        this.idValidator = idValidator;
    }

    @Override
    public boolean existsById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);

        return requirementRepository.existsById(id);
    }

    @Override
    public void strictExistsById(Long id) {
        if (!existsById(id)) {
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id));
        }
    }

    @Override
    public Set<RequirementDto> findAll() {
        return requirementRepository
                .findAll()
                .stream()
                .map(requirementMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public RequirementDto findById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        return requirementRepository
                .findById(id)
                .map(requirementMapper::toDto)
                .orElseThrow(() ->
                        new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id)));
    }

    @Override
    public RequirementDto save(Requirement requirement) {
        idValidator.validateNoIdFroCreate(requirement.getId(), ENTITY_NAME);
        requirementValidator.validate(requirement);
        return requirementMapper.toDto(requirementRepository.save(requirement));
    }

    @Override
    @Transactional
    public void delete(Requirement requirement) {
        if (requirement == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }
        strictExistsById(requirement.getId());
        requirementRepository.delete(requirement);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        strictExistsById(id);
        requirementRepository.deleteById(id);
    }

    @Override
    @Transactional
    public RequirementDto update(Long id, RequirementDto requirement) {
        strictExistsById(requirement.getId());
        requirement.setId(id);
        requirementValidator.validateDto(requirement);
        return requirementMapper.toDto(requirementRepository.save(requirementMapper.toEntity(requirement)));
    }

    @Override
    public Set<RequirementDto> findAllByJobOfferId(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        return requirementRepository
                .findAllByJobOfferId(id)
                .stream()
                .map(requirementMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteAllByJobOfferId(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        requirementRepository.deleteAllByJobOfferId(id);
    }
}
