package miwm.job4me.repositories.cv;

import miwm.job4me.model.cv.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByEmployeeId(Long employeeId);

    void deleteAllByEmployeeId(Long employeeId);
}
