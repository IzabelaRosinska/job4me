package miwm.job4me.services.offer.listDisplay;

import miwm.job4me.web.model.filters.JobOfferFilterDto;
import miwm.job4me.web.model.listDisplay.ListDisplayDto;
import miwm.job4me.web.model.listDisplay.ListDisplaySavedDto;
import org.springframework.data.domain.Page;

public interface JobOfferListDisplayService {
    Page<ListDisplayDto> findAllOffers(int page, int size, String order);

    Page<ListDisplayDto> findAllOffersByFilter(int page, int size, JobOfferFilterDto jobOfferFilterDto);

    Page<ListDisplayDto> findAllOffersOfEmployer(int page, int size, String order, String direction, Long employerId);

    Page<ListDisplayDto> findAllOffersOfEmployerByFilter(int page, int size, JobOfferFilterDto jobOfferFilterDto, Long employerId);

    Page<ListDisplayDto> findAllOffersOfJobFair(int page, int size, String order, Long jobFairId);

    Page<ListDisplayDto> findAllOffersOfJobFairByFilter(int page, int size, JobOfferFilterDto jobOfferFilterDto, Long jobFairId);

    Page<ListDisplaySavedDto> findAllSavedOffersEmployeeView(int page, int size, String order, Long userId);

    Page<ListDisplaySavedDto> findAllSavedOffersByFilterEmployeeView(int page, int size, JobOfferFilterDto jobOfferFilterDto, Long userId);

    Page<ListDisplaySavedDto> findAllOffersEmployeeView(int page, int size, String order);

    Page<ListDisplaySavedDto> findAllOffersByFilterEmployeeView(int page, int size, JobOfferFilterDto jobOfferFilterDto);

    Page<ListDisplaySavedDto> findAllOffersOfEmployerEmployeeView(int page, int size, String order, String direction, Long employerId);

    Page<ListDisplaySavedDto> findAllOffersOfEmployerByFilterEmployeeView(int page, int size, JobOfferFilterDto jobOfferFilterDto, Long employerId);

    Page<ListDisplaySavedDto> findAllOffersOfJobFairEmployeeView(int page, int size, String order, Long jobFairId);

    Page<ListDisplaySavedDto> findAllOffersOfJobFairByFilterEmployeeView(int page, int size, JobOfferFilterDto jobOfferFilterDto, Long jobFairId);

}
