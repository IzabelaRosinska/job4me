package miwm.job4me.repositories;

import miwm.job4me.model.cv.Education;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<Education, Long> {
}
