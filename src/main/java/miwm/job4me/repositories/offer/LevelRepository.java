package miwm.job4me.repositories.offer;

import miwm.job4me.model.offer.Level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {
    @Query("SELECT l FROM Level l WHERE :name IS NULL OR LOWER(l.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Level> findByNameContaining(Pageable pageable, String name);

    boolean existsByName(String name);

    Level findByName(String name);
}
