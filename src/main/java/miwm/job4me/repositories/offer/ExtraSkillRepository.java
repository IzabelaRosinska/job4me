package miwm.job4me.repositories.offer;

import miwm.job4me.model.offer.ExtraSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtraSkillRepository extends JpaRepository<ExtraSkill, Long> {
}
