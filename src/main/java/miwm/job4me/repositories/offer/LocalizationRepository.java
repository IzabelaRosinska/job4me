package miwm.job4me.repositories.offer;

import miwm.job4me.model.offer.Localization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalizationRepository extends JpaRepository<Localization, Long> {
    @Query("SELECT l FROM Localization l WHERE :city IS NULL OR LOWER(l.city) LIKE LOWER(:city)")
    Page<Localization> findByCityContaining(Pageable pageable, String city);

    boolean existsByCity(String city);

    Localization findByCity(String city);
}
