package miwm.job4me.repositories.offer;

import miwm.job4me.model.offer.SavedOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SavedOfferRepository extends JpaRepository<SavedOffer, Long> {

    @Query(nativeQuery = true, value = "select * from saved_offers where offer_id = ?1 AND employee_id = ?2")
    Optional<SavedOffer> findByIds(Long offerId, Long employeeId);
}
