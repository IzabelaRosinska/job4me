package miwm.job4me.services.recommendation;

import miwm.job4me.web.model.filters.JobOfferFilterDto;
import miwm.job4me.web.model.listDisplay.ListDisplaySavedDto;
import org.springframework.data.domain.Page;

public interface RecommendationService {
    Page<ListDisplaySavedDto> getRecommendedOffers(int page, int size, Long jobFairId);

    Page<ListDisplaySavedDto> getRecommendedOffersByFilter(int page, int size, JobOfferFilterDto jobOfferFilterDto, Long jobFairId);
}
