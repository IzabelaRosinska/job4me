package miwm.job4me.repositories.offer;

import miwm.job4me.model.offer.Industry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IndustryRepository extends JpaRepository<Industry, Long> {
    @Query("SELECT i FROM Industry i WHERE :name IS NULL OR LOWER(i.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Industry> findByNameContaining(Pageable pageable, String name);

    boolean existsByName(String name);

    Industry findByName(String name);
}
