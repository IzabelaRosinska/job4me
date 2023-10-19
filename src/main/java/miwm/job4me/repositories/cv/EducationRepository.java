package miwm.job4me.repositories.cv;

import miwm.job4me.model.cv.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {
    List<Education> findAllByEmployeeId(Long employeeId);

    void deleteAllByEmployeeId(Long employeeId);
}
