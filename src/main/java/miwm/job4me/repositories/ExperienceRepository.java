package miwm.job4me.repositories;

import miwm.job4me.model.cv.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
}
