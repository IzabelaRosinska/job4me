package miwm.job4me.repositories.cv;

import miwm.job4me.model.cv.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findAllByEmployeeId(Long employeeId);

    void deleteAllByEmployeeId(Long employeeId);
}
