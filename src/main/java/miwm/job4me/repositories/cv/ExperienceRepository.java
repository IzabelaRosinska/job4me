package miwm.job4me.repositories.cv;

import miwm.job4me.model.cv.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findAllByEmployeeId(Long employeeId);

    void deleteAllByEmployeeId(Long employeeId);

}
