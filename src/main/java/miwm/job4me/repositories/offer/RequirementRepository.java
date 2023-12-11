package miwm.job4me.repositories.offer;

import miwm.job4me.model.offer.description.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequirementRepository extends JpaRepository<Requirement, Long> {
    List<Requirement> findAllByJobOfferId(Long jobOfferId);

    void deleteAllByJobOfferId(Long jobOfferId);
}
