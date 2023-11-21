package miwm.job4me.repositories.offer;

import miwm.job4me.model.offer.SavedOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SavedOfferRepository extends JpaRepository<SavedOffer, Long> {
    Set<Long> findAllOfferIdsByEmployee_Id(Long id);

    @Query(nativeQuery = true, value = "select * from saved_offers where offer_id = ?1 AND employee_id = ?2")
    Optional<SavedOffer> findByIds(Long offerId, Long employeeId);

    @Query(nativeQuery = true, value = "select * from saved_offers where employee_id = ?1")
    List<SavedOffer> getSavedForEmployee(Long employeeId);
}
