package miwm.job4me.services.offer;

import miwm.job4me.model.offer.JobOffer;
import miwm.job4me.services.BaseDtoService;
import miwm.job4me.web.model.filters.JobOfferFilterDto;
import miwm.job4me.web.model.listDisplay.ListDisplayDto;
import miwm.job4me.web.model.offer.JobOfferDto;
import org.springframework.data.domain.Page;

public interface JobOfferService extends BaseDtoService<JobOffer, JobOfferDto, Long> {
    Page<JobOfferDto> findByFilters(int page, int size, String city, String employmentFormName, String levelName, String contractTypeName, Integer salaryFrom, Integer salaryTo, String industryName, String offerName);

    Page<ListDisplayDto> findListDisplayByFilters(int page, int size, JobOfferFilterDto jobOfferFilterDto);

    JobOfferDto saveDto(JobOfferDto jobOfferDto);

    boolean existsById(Long id);

    void strictExistsById(Long id);

    JobOfferDto update(Long id, JobOfferDto jobOffer);

    JobOffer findOfferById(Long id);

    JobOfferDto activateOffer(Long id);

    JobOfferDto deactivateOffer(Long id);
}
