package miwm.job4me.repositories.offer;

import miwm.job4me.model.offer.EmploymentForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmploymentFormRepository extends JpaRepository<EmploymentForm, Long> {
    @Query("SELECT e FROM EmploymentForm e WHERE (:name IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%')))")
    Page<EmploymentForm> findByNameContaining(Pageable pageable, String name);

    boolean existsByName(String name);

    EmploymentForm findByName(String name);
}
