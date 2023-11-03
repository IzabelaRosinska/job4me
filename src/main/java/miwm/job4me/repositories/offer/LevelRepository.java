package miwm.job4me.repositories.offer;

import miwm.job4me.model.offer.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {
}
