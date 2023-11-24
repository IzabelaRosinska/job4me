package miwm.job4me.services.offer;

import miwm.job4me.model.offer.JobOffer;
import miwm.job4me.services.BaseDtoService;
import miwm.job4me.web.model.filters.JobOfferFilterDto;
import miwm.job4me.web.model.offer.JobOfferDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface JobOfferService extends BaseDtoService<JobOffer, JobOfferDto, Long> {
    Page<JobOfferDto> findAllByPage(int page, int size, String order);

    Page<JobOffer> findByPage(int page, int size, String order, Boolean isActive, List<Long> offerIds);

    Page<JobOffer> findByFilters(int page, int size, String order, JobOfferFilterDto jobOfferFilterDto, List<Long> employerIds, Boolean isActive, List<Long> offerIds);

    Page<JobOffer> findAllOffersOfEmployers(int page, int size, String order, List<Long> employerIds, Boolean isActive, List<Long> offerIds);

    Page<JobOffer> findRecommendedOffers(int page, int size, List<Long> offerIds);

    Page<JobOffer> findRecommendedOffersByFilters(int page, int size, JobOfferFilterDto jobOfferFilterDto, List<Long> offerIds);

    JobOfferDto saveDto(JobOfferDto jobOfferDto);

    boolean existsById(Long id);

    void strictExistsById(Long id);

    JobOfferDto update(Long id, JobOfferDto jobOffer);

    JobOffer findOfferById(Long id);

    JobOfferDto activateOffer(Long id);

    JobOfferDto deactivateOffer(Long id);
}
