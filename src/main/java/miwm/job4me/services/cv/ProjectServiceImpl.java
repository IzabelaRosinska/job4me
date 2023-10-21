package miwm.job4me.services.cv;

import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Project;
import miwm.job4me.repositories.cv.ProjectRepository;
import miwm.job4me.validators.entity.IdValidator;
import miwm.job4me.validators.entity.cv.ProjectValidator;
import miwm.job4me.web.mappers.cv.ProjectMapper;
import miwm.job4me.web.model.cv.ProjectDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;
    private ProjectMapper projectMapper;
    private ProjectValidator projectValidator;
    private IdValidator idValidator;
    private final String entityName = "Project";

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, ProjectValidator projectValidator, IdValidator idValidator) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.projectValidator = projectValidator;
        this.idValidator = idValidator;
    }

    private void existsById(Long id) {
        idValidator.validateLongId(id, entityName);
        projectRepository.findById(id).orElseThrow(() ->
                new NoSuchElementFoundException(ExceptionMessages.elementNotFound(entityName, id)));
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
        idValidator.validateLongId(id, entityName);

        return projectRepository
                .findById(id)
                .map(projectMapper::toDto)
                .orElseThrow(() ->
                        new NoSuchElementFoundException(ExceptionMessages.elementNotFound(entityName, id)));
    }

    @Override
    public ProjectDto save(Project object) {
        projectValidator.validate(object);
        return projectMapper.toDto(projectRepository.save(object));
    }

    @Override
    public void delete(Project object) {
        existsById(object.getId());
        projectRepository.delete(object);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        existsById(id);
        projectRepository.deleteById(id);
    }

    @Transactional
    public ProjectDto update(ProjectDto project) {
        existsById(project.getId());
        projectValidator.validateDto(project);
        return projectMapper.toDto(projectRepository.save(projectMapper.toEntity(project)));
    }

    public Set<ProjectDto> findAllByEmployeeId(Long employeeId) {
        idValidator.validateLongId(employeeId, entityName);
        return projectRepository
                .findAllByEmployeeId(employeeId)
                .stream()
                .map(projectMapper::toDto)
                .collect(java.util.stream.Collectors.toSet());
    }

    public void deleteAllByEmployeeId(Long employeeId) {
        idValidator.validateLongId(employeeId, entityName);
        projectRepository.deleteAllByEmployeeId(employeeId);
    }
}