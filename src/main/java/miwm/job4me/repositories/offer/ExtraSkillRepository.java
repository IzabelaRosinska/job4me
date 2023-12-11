package miwm.job4me.repositories.offer;

import miwm.job4me.model.offer.description.ExtraSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtraSkillRepository extends JpaRepository<ExtraSkill, Long> {
    List<ExtraSkill> findAllByJobOfferId(Long id);

    void deleteAllByJobOfferId(Long id);
}
