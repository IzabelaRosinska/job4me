package miwm.job4me.services.offer.listDisplay;

import miwm.job4me.web.model.filters.JobOfferFilterDto;
import miwm.job4me.web.model.listDisplay.ListDisplayDto;
import miwm.job4me.web.model.listDisplay.ListDisplaySavedDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface JobOfferListDisplayService {
    Page<ListDisplayDto> findAllActiveOffers(int page, int size, String order);

    Page<ListDisplayDto> findAllActiveOffersByFilter(int page, int size, String order, JobOfferFilterDto jobOfferFilterDto);

    Page<ListDisplayDto> findAllActiveOffersOfEmployer(int page, int size, String order, Long employerId);

    Page<ListDisplayDto> findAllActiveOffersOfEmployerByFilter(int page, int size, String order, JobOfferFilterDto jobOfferFilterDto, Long employerId);

    Page<ListDisplayDto> findAllOffersOfEmployerEmployerView(int page, int size, String order, Boolean isActive);

    Page<ListDisplayDto> findAllOffersOfEmployerByFilterEmployerView(int page, int size, String order, JobOfferFilterDto jobOfferFilterDto, Boolean isActive);

    Page<ListDisplayDto> findAllActiveOffersOfJobFair(int page, int size, String order, Long jobFairId);

    Page<ListDisplayDto> findAllActiveOffersOfJobFairByFilter(int page, int size, String order, JobOfferFilterDto jobOfferFilterDto, Long jobFairId);

    Page<ListDisplaySavedDto> findAllSavedOffersEmployeeView(int page, int size, String order);

    Page<ListDisplaySavedDto> findAllSavedOffersByFilterEmployeeView(int page, int size, String order, JobOfferFilterDto jobOfferFilterDto);

    Page<ListDisplaySavedDto> findAllOffersEmployeeView(int page, int size, String order);

    Page<ListDisplaySavedDto> findAllOffersByFilterEmployeeView(int page, int size, String order, JobOfferFilterDto jobOfferFilterDto);

    Page<ListDisplaySavedDto> findAllOffersOfEmployerEmployeeView(int page, int size, String order, Long employerId);

    Page<ListDisplaySavedDto> findAllOffersOfEmployerByFilterEmployeeView(int page, int size, String order, JobOfferFilterDto jobOfferFilterDto, Long employerId);

    Page<ListDisplaySavedDto> findAllOffersOfJobFairEmployeeView(int page, int size, String order, Long jobFairId);

    Page<ListDisplaySavedDto> findAllOffersOfJobFairByFilterEmployeeView(int page, int size, String order, JobOfferFilterDto jobOfferFilterDto, Long jobFairId);

    Page<ListDisplaySavedDto> findAllRecommendedOffersEmployeeView(int page, int size, List<Long> offerIds);

    Page<ListDisplaySavedDto> findAllRecommendedOffersByFilterEmployeeView(int page, int size, JobOfferFilterDto jobOfferFilterDto, List<Long> offerIds);
}
