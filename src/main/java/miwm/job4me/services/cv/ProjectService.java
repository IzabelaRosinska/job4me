package miwm.job4me.services.cv;

import miwm.job4me.model.cv.Project;
import miwm.job4me.services.BaseDtoService;
import miwm.job4me.web.model.cv.ProjectDto;

import java.util.Set;

public interface ProjectService extends BaseDtoService<Project, ProjectDto, Long> {
    void strictExistsById(Long id);

    boolean existsById(Long id);

    ProjectDto update(ProjectDto project);

    Set<ProjectDto> findAllByEmployeeId(Long employeeId);

    void deleteAllByEmployeeId(Long employeeId);
}
