//package miwm.job4me.services.offer.listDisplay;
//
//import miwm.job4me.web.mappers.listDisplay.ListDisplayMapper;
//import miwm.job4me.web.model.filters.JobOfferFilterDto;
//import miwm.job4me.web.model.listDisplay.ListDisplayDto;
//import miwm.job4me.web.model.listDisplay.ListDisplaySavedDto;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//
//@Service
//public class JobOfferListDisplayServiceImpl implements JobOfferListDisplayService {
//    private final ListDisplayMapper listDisplayMapper;
//    private final ListDisplaySavedDto listDisplaySavedDto;
//    private final
//
//    private final Map<String, String> orderMap = Map.of(
//            "1", "j.salaryFrom ASC",
//            "2", "j.salaryFrom DESC",
//            "3", "j.offerName ASC",
//            "4", "j.offerName DESC"
//    );
//    @Override
//    public Page<ListDisplayDto> findListDisplayByFilters(int page, int size, JobOfferFilterDto jobOfferFilterDto) {
//        return jobOfferRepository
//                .findByFilters2(PageRequest.of(page, size), jobOfferFilterDto.getCities())
//                .map(listDisplayMapper::toDtoFromJobOffer);
//    }
//
//
//    @Override
//    public Page<ListDisplayDto> findAllOffers(int page, int size, String order) {
//        return null;
//    }
//
//    @Override
//    public Page<ListDisplayDto> findAllOffersByFilter(int page, int size, JobOfferFilterDto jobOfferFilterDto) {
//        return null;
//    }
//
//    @Override
//    public Page<ListDisplayDto> findAllOffersOfEmployer(int page, int size, String order, String direction, Long employerId) {
//        return null;
//    }
//
//    @Override
//    public Page<ListDisplayDto> findAllOffersOfEmployerByFilter(int page, int size, JobOfferFilterDto jobOfferFilterDto, Long employerId) {
//        return null;
//    }
//
//    @Override
//    public Page<ListDisplayDto> findAllOffersOfJobFair(int page, int size, String order, Long jobFairId) {
//        return null;
//    }
//
//    @Override
//    public Page<ListDisplayDto> findAllOffersOfJobFairByFilter(int page, int size, JobOfferFilterDto jobOfferFilterDto, Long jobFairId) {
//        return null;
//    }
//
//    @Override
//    public Page<ListDisplaySavedDto> findAllSavedOffersEmployeeView(int page, int size, String order, Long userId) {
//        return null;
//    }
//
//    @Override
//    public Page<ListDisplaySavedDto> findAllSavedOffersByFilterEmployeeView(int page, int size, JobOfferFilterDto jobOfferFilterDto, Long userId) {
//        return null;
//    }
//
//    @Override
//    public Page<ListDisplaySavedDto> findAllOffersEmployeeView(int page, int size, String order) {
//        return null;
//    }
//
//    @Override
//    public Page<ListDisplaySavedDto> findAllOffersByFilterEmployeeView(int page, int size, JobOfferFilterDto jobOfferFilterDto) {
//        return null;
//    }
//
//    @Override
//    public Page<ListDisplaySavedDto> findAllOffersOfEmployerEmployeeView(int page, int size, String order, String direction, Long employerId) {
//        return null;
//    }
//
//    @Override
//    public Page<ListDisplaySavedDto> findAllOffersOfEmployerByFilterEmployeeView(int page, int size, JobOfferFilterDto jobOfferFilterDto, Long employerId) {
//        return null;
//    }
//
//    @Override
//    public Page<ListDisplaySavedDto> findAllOffersOfJobFairEmployeeView(int page, int size, String order, Long jobFairId) {
//        return null;
//    }
//
//    @Override
//    public Page<ListDisplaySavedDto> findAllOffersOfJobFairByFilterEmployeeView(int page, int size, JobOfferFilterDto jobOfferFilterDto, Long jobFairId) {
//        return null;
//    }
//}
