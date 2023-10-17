package miwm.job4me.repositories;

import miwm.job4me.model.cv.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
