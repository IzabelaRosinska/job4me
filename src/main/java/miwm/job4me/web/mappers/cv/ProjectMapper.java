package miwm.job4me.web.mappers.cv;

import miwm.job4me.model.cv.Project;
import miwm.job4me.web.model.cv.ProjectDto;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public ProjectDto toDto(Project project) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(project.getId());
        projectDto.setDescription(project.getDescription());
        projectDto.setEmployeeId(project.getEmployee().getId());
        return projectDto;
    }

    public Project toEntity(ProjectDto projectDto) {
        Project project = new Project();
        project.setId(projectDto.getId());
        project.setDescription(projectDto.getDescription());
        return project;
    }
}
