package miwm.job4me.services.cv;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Project;
import miwm.job4me.repositories.cv.ProjectRepository;
import miwm.job4me.validators.entity.cv.ProjectValidator;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.web.mappers.cv.ProjectMapper;
import miwm.job4me.web.model.cv.ProjectDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final ProjectValidator projectValidator;
    private final IdValidator idValidator;
    private final String ENTITY_NAME = "Project";

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, ProjectValidator projectValidator, IdValidator idValidator) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.projectValidator = projectValidator;
        this.idValidator = idValidator;
    }

    @Override
    public boolean existsById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);

        return projectRepository.existsById(id);
    }

    @Override
    public void strictExistsById(Long id) {
        if (!existsById(id)) {
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id));
        }
    }

    @Override
    public Set<ProjectDto> findAll() {
        return projectRepository
                .findAll()
                .stream()
                .map(projectMapper::toDto)
                .collect(java.util.stream.Collectors.toSet());
    }

    @Override
    public ProjectDto findById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        return projectRepository
                .findById(id)
                .map(projectMapper::toDto)
                .orElseThrow(() ->
                        new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id)));
    }

    @Override
    public ProjectDto save(Project project) {
        projectValidator.validate(project);
        return projectMapper.toDto(projectRepository.save(project));
    }

    @Override
    public void delete(Project project) {
        if (project == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }
        strictExistsById(project.getId());
        projectRepository.delete(project);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        strictExistsById(id);
        projectRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ProjectDto update(ProjectDto project) {
        projectValidator.validateDto(project);
        strictExistsById(project.getId());
        return projectMapper.toDto(projectRepository.save(projectMapper.toEntity(project)));
    }

    @Override
    public Set<ProjectDto> findAllByEmployeeId(Long employeeId) {
        idValidator.validateLongId(employeeId, ENTITY_NAME);
        return projectRepository
                .findAllByEmployeeId(employeeId)
                .stream()
                .map(projectMapper::toDto)
                .collect(java.util.stream.Collectors.toSet());
    }

    @Override
    public void deleteAllByEmployeeId(Long employeeId) {
        idValidator.validateLongId(employeeId, ENTITY_NAME);
        projectRepository.deleteAllByEmployeeId(employeeId);
    }
}
