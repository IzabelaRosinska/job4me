package miwm.job4me.web.controllers.offer.listDisplay;

import io.swagger.v3.oas.annotations.Operation;
import miwm.job4me.services.offer.listDisplay.JobOfferListDisplayService;
import miwm.job4me.web.model.filters.JobOfferFilterDto;
import miwm.job4me.web.model.listDisplay.ListDisplayDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class JobOfferListDisplayController {
    private final JobOfferListDisplayService jobOfferListDisplayService;

    public JobOfferListDisplayController(JobOfferListDisplayService jobOfferListDisplayService) {
        this.jobOfferListDisplayService = jobOfferListDisplayService;
    }

    @GetMapping("job-offers/list-display")
    @Operation(summary = "Get all job offers", description = "Gets all job offers from database and returns them in list display dto")
    public ResponseEntity<Page<ListDisplayDto>> getAllActiveOffers(@RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size,
                                                                   @RequestParam(defaultValue = "1") String order) {
        return new ResponseEntity<>(jobOfferListDisplayService.findAllActiveOffers(page, size, order), HttpStatus.OK);
    }

    @GetMapping("job-offers/list-display/filter")
    @Operation(summary = "Get all job offers by filter", description = "Gets all job offers from database by filter and returns them in list display dto")
    public ResponseEntity<Page<ListDisplayDto>> getAllActiveOffersByFilter(@RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size,
                                                                           @RequestParam(defaultValue = "1") String order,
                                                                           @RequestBody JobOfferFilterDto jobOfferFilterDto) {
        return new ResponseEntity<>(jobOfferListDisplayService.findAllActiveOffersByFilter(page, size, order, jobOfferFilterDto), HttpStatus.OK);
    }

    @GetMapping("job-offers/list-display/employer/{employerId}")
    @Operation(summary = "Get all job offers of employer", description = "Gets all job offers of employer from database and returns them in list display dto")
    public ResponseEntity<Page<ListDisplayDto>> getAllActiveOffersOfEmployer(@RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "10") int size,
                                                                             @RequestParam(defaultValue = "1") String order,
                                                                             @PathVariable Long employerId) {
        return new ResponseEntity<>(jobOfferListDisplayService.findAllActiveOffersOfEmployer(page, size, order, employerId), HttpStatus.OK);
    }

    @GetMapping("job-offers/list-display/employer/{employerId}/filter")
    @Operation(summary = "Get all job offers of employer by filter", description = "Gets all job offers of employer from database by filter and returns them in list display dto")
    public ResponseEntity<Page<ListDisplayDto>> getAllActiveOffersOfEmployerByFilter(@RequestParam(defaultValue = "0") int page,
                                                                                     @RequestParam(defaultValue = "10") int size,
                                                                                     @RequestParam(defaultValue = "1") String order,
                                                                                     @RequestBody JobOfferFilterDto jobOfferFilterDto,
                                                                                     @PathVariable Long employerId) {
        return new ResponseEntity<>(jobOfferListDisplayService.findAllActiveOffersOfEmployerByFilter(page, size, order, jobOfferFilterDto, employerId), HttpStatus.OK);
    }

    @GetMapping("employer/job-offers/list-display")
    @Operation(summary = "Get all job offers of employer", description = "Gets all job offers of employer from database and returns them in list display dto")
    public ResponseEntity<Page<ListDisplayDto>> getAllOffersOfEmployerEmployerView(@RequestParam(defaultValue = "0") int page,
                                                                                   @RequestParam(defaultValue = "10") int size,
                                                                                   @RequestParam(defaultValue = "1") String order,
                                                                                   @RequestParam(defaultValue = "null") Boolean isActive) {
        return new ResponseEntity<>(jobOfferListDisplayService.findAllOffersOfEmployerEmployerView(page, size, order, isActive), HttpStatus.OK);
    }

    @GetMapping("employer/job-offers/list-display/filter")
    @Operation(summary = "Get all job offers of employer by filter", description = "Gets all job offers of employer from database by filter and returns them in list display dto")
    public ResponseEntity<Page<ListDisplayDto>> getAllOffersOfEmployerByFilterEmployerView(@RequestParam(defaultValue = "0") int page,
                                                                                           @RequestParam(defaultValue = "10") int size,
                                                                                           @RequestParam(defaultValue = "1") String order,
                                                                                           @RequestBody JobOfferFilterDto jobOfferFilterDto,
                                                                                           @RequestParam(defaultValue = "null") Boolean isActive) {
        return new ResponseEntity<>(jobOfferListDisplayService.findAllOffersOfEmployerByFilterEmployerView(page, size, order, jobOfferFilterDto, isActive), HttpStatus.OK);
    }

//    Page<ListDisplayDto> findAllActiveOffersOfJobFair(int page, int size, String order, Long jobFairId);
//
//    Page<ListDisplayDto> findAllActiveOffersOfJobFairByFilter(int page, int size, String order, JobOfferFilterDto jobOfferFilterDto, Long jobFairId);
//
//    Page<ListDisplaySavedDto> findAllSavedOffersEmployeeView(int page, int size, String order);
//
//    Page<ListDisplaySavedDto> findAllSavedOffersByFilterEmployeeView(int page, int size, String order, JobOfferFilterDto jobOfferFilterDto);
//
//    Page<ListDisplaySavedDto> findAllOffersEmployeeView(int page, int size, String order);
//
//    Page<ListDisplaySavedDto> findAllOffersByFilterEmployeeView(int page, int size, String order, JobOfferFilterDto jobOfferFilterDto);
//
//    Page<ListDisplaySavedDto> findAllOffersOfEmployerEmployeeView(int page, int size, String order, String direction, Long employerId);
//
//    Page<ListDisplaySavedDto> findAllOffersOfEmployerByFilterEmployeeView(int page, int size, String order, JobOfferFilterDto jobOfferFilterDto, Long employerId);
//
//    Page<ListDisplaySavedDto> findAllOffersOfJobFairEmployeeView(int page, int size, String order, Long jobFairId);
//
//    Page<ListDisplaySavedDto> findAllOffersOfJobFairByFilterEmployeeView(int page, int size, String order, JobOfferFilterDto jobOfferFilterDto, Long jobFairId);


}
