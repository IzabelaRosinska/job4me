package miwm.job4me.services.offer;

import miwm.job4me.model.offer.SavedOffer;
import miwm.job4me.services.BaseService;
import miwm.job4me.web.model.offer.JobOfferReviewDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SavedOfferService extends BaseService<SavedOffer, Long> {

    List<SavedOffer> getSavedForEmployeeWithId(Long employeeId);

    Optional<SavedOffer> findByIds(Long employeeId, Long offerId);

    Boolean checkIfSaved(Long id);

    List<JobOfferReviewDto> getSavedOffers();

    void deleteOfferFromSaved(Long id);

    void addOfferToSaved(Long id);

    JobOfferReviewDto findOfferWithIdByUser(Long id);

    Set<Long> findAllOfferIdsForCurrentEmployee();
}
