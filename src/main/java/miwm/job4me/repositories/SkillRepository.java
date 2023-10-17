package miwm.job4me.repositories;

import miwm.job4me.model.cv.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
