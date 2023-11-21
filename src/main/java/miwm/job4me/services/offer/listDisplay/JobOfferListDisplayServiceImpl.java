package miwm.job4me.services.offer.listDisplay;

import miwm.job4me.services.event.JobFairEmployerParticipationService;
import miwm.job4me.services.offer.JobOfferService;
import miwm.job4me.services.offer.SavedOfferService;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.web.mappers.listDisplay.ListDisplayMapper;
import miwm.job4me.web.mappers.listDisplay.ListDisplaySavedMapper;
import miwm.job4me.web.model.filters.JobOfferFilterDto;
import miwm.job4me.web.model.listDisplay.ListDisplayDto;
import miwm.job4me.web.model.listDisplay.ListDisplaySavedDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobOfferListDisplayServiceImpl implements JobOfferListDisplayService {
    private final ListDisplayMapper listDisplayMapper;
    private final ListDisplaySavedMapper listDisplaySavedMapper;
    private final JobFairEmployerParticipationService jobFairEmployerParticipationService;
    private final JobOfferService jobOfferService;
    private final SavedOfferService savedOfferService;

    private final IdValidator idValidator;
    private final String ENTITY_EMPLOYER = "Employer";
    private final String ENTITY_JOB_FAIR = "JobFair";

    public JobOfferListDisplayServiceImpl(ListDisplayMapper listDisplayMapper, ListDisplaySavedMapper listDisplaySavedMapper, JobFairEmployerParticipationService jobFairEmployerParticipationService, JobOfferService jobOfferService, SavedOfferService savedOfferService, IdValidator idValidator) {
        this.listDisplayMapper = listDisplayMapper;
        this.listDisplaySavedMapper = listDisplaySavedMapper;
        this.jobFairEmployerParticipationService = jobFairEmployerParticipationService;
        this.jobOfferService = jobOfferService;
        this.savedOfferService = savedOfferService;
        this.idValidator = idValidator;
    }

    @Override
    public Page<ListDisplayDto> findAllActiveOffers(int page, int size, String order) {
        return jobOfferService
                .findByPage(page, size, order, true)
                .map(listDisplayMapper::toDtoFromJobOffer);
    }

    @Override
    public Page<ListDisplayDto> findAllActiveOffersByFilter(int page, int size, String order, JobOfferFilterDto jobOfferFilterDto) {
        return jobOfferService
                .findByFilters(page, size, order, jobOfferFilterDto, null, true)
                .map(listDisplayMapper::toDtoFromJobOffer);
    }

    @Override
    public Page<ListDisplayDto> findAllActiveOffersOfEmployer(int page, int size, String order, String direction, Long employerId) {
        idValidator.validateLongId(employerId, ENTITY_EMPLOYER);

        return jobOfferService
                .findAllOffersOfEmployers(page, size, order, List.of(employerId), true)
                .map(listDisplayMapper::toDtoFromJobOffer);
    }

    @Override
    public Page<ListDisplayDto> findAllActiveOffersOfEmployerByFilter(int page, int size, String order, JobOfferFilterDto jobOfferFilterDto, Long employerId) {
        idValidator.validateLongId(employerId, ENTITY_EMPLOYER);

        return jobOfferService
                .findByFilters(page, size, order, jobOfferFilterDto, List.of(employerId), true)
                .map(listDisplayMapper::toDtoFromJobOffer);
    }

    @Override
    public Page<ListDisplayDto> findAllOffersOfEmployerEmployerView(int page, int size, String order, String direction, Long employerId, Boolean isActive) {
        idValidator.validateLongId(employerId, ENTITY_EMPLOYER);

        return jobOfferService
                .findAllOffersOfEmployers(page, size, order, List.of(employerId), isActive)
                .map(listDisplayMapper::toDtoFromJobOffer);
    }

    @Override
    public Page<ListDisplayDto> findAllOffersOfEmployerByFilterEmployerView(int page, int size, String order, JobOfferFilterDto jobOfferFilterDto, Long employerId, Boolean isActive) {
        idValidator.validateLongId(employerId, ENTITY_EMPLOYER);

        return jobOfferService
                .findByFilters(page, size, order, jobOfferFilterDto, List.of(employerId), isActive)
                .map(listDisplayMapper::toDtoFromJobOffer);
    }


    @Override
    public Page<ListDisplayDto> findAllActiveOffersOfJobFair(int page, int size, String order, Long jobFairId) {
        idValidator.validateLongId(jobFairId, ENTITY_JOB_FAIR);

        List<Long> employersIds = jobFairEmployerParticipationService
                .findAllEmployersIdsForJobFair(jobFairId)
                .stream()
                .toList();

        return jobOfferService
                .findAllOffersOfEmployers(page, size, order, employersIds, true)
                .map(listDisplayMapper::toDtoFromJobOffer);
    }

    @Override
    public Page<ListDisplayDto> findAllActiveOffersOfJobFairByFilter(int page, int size, String order, JobOfferFilterDto jobOfferFilterDto, Long jobFairId) {
        idValidator.validateLongId(jobFairId, ENTITY_JOB_FAIR);

        List<Long> employersIds = jobFairEmployerParticipationService
                .findAllEmployersIdsForJobFair(jobFairId)
                .stream()
                .toList();

        return jobOfferService
                .findByFilters(page, size, order, jobOfferFilterDto, employersIds, true)
                .map(listDisplayMapper::toDtoFromJobOffer);
    }

    @Override
    public Page<ListDisplaySavedDto> findAllSavedOffersEmployeeView(int page, int size, String order) {
        return null;
    }

    @Override
    public Page<ListDisplaySavedDto> findAllSavedOffersByFilterEmployeeView(int page, int size, String order, JobOfferFilterDto jobOfferFilterDto) {
        return null;
    }

    @Override
    public Page<ListDisplaySavedDto> findAllOffersEmployeeView(int page, int size, String order) {
        return null;
    }

    @Override
    public Page<ListDisplaySavedDto> findAllOffersByFilterEmployeeView(int page, int size, String order, JobOfferFilterDto jobOfferFilterDto) {
        return null;
    }

    @Override
    public Page<ListDisplaySavedDto> findAllOffersOfEmployerEmployeeView(int page, int size, String order, String direction, Long employerId) {
        return null;
    }

    @Override
    public Page<ListDisplaySavedDto> findAllOffersOfEmployerByFilterEmployeeView(int page, int size, String order, JobOfferFilterDto jobOfferFilterDto, Long employerId) {
        return null;
    }

    @Override
    public Page<ListDisplaySavedDto> findAllOffersOfJobFairEmployeeView(int page, int size, String order, Long jobFairId) {
        return null;
    }

    @Override
    public Page<ListDisplaySavedDto> findAllOffersOfJobFairByFilterEmployeeView(int page, int size, String order, JobOfferFilterDto jobOfferFilterDto, Long jobFairId) {
        return null;
    }

}
