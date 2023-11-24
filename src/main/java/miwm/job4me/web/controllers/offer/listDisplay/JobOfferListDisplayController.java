package miwm.job4me.web.controllers.offer.listDisplay;

import io.swagger.v3.oas.annotations.Operation;
import miwm.job4me.services.offer.listDisplay.JobOfferListDisplayService;
import miwm.job4me.web.model.filters.JobOfferFilterDto;
import miwm.job4me.web.model.listDisplay.ListDisplayDto;
import miwm.job4me.web.model.listDisplay.ListDisplaySavedDto;
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

    @PostMapping("job-offers/list-display/filter")
    @Operation(summary = "Get all job offers by filter (get with body)", description = "Gets all job offers from database by filter and returns them in list display dto")
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

    @PostMapping("job-offers/list-display/employer/{employerId}/filter")
    @Operation(summary = "Get all job offers of employer by filter (get with body)", description = "Gets all job offers of employer from database by filter and returns them in list display dto")
    public ResponseEntity<Page<ListDisplayDto>> getAllActiveOffersOfEmployerByFilter(@RequestParam(defaultValue = "0") int page,
                                                                                     @RequestParam(defaultValue = "10") int size,
                                                                                     @RequestParam(defaultValue = "1") String order,
                                                                                     @RequestBody JobOfferFilterDto jobOfferFilterDto,
                                                                                     @PathVariable Long employerId) {
        return new ResponseEntity<>(jobOfferListDisplayService.findAllActiveOffersOfEmployerByFilter(page, size, order, jobOfferFilterDto, employerId), HttpStatus.OK);
    }

    @GetMapping("employer/job-offers/list-display")
    @Operation(summary = "Get all job offers of logged in employer", description = "Gets all job offers of logged in  employer from database and returns them in list display dto")
    public ResponseEntity<Page<ListDisplayDto>> getAllOffersOfEmployerEmployerView(@RequestParam(defaultValue = "0") int page,
                                                                                   @RequestParam(defaultValue = "10") int size,
                                                                                   @RequestParam(defaultValue = "1") String order,
                                                                                   @RequestParam(defaultValue = "null") Boolean isActive) {
        return new ResponseEntity<>(jobOfferListDisplayService.findAllOffersOfEmployerEmployerView(page, size, order, isActive), HttpStatus.OK);
    }

    @PostMapping("employer/job-offers/list-display/filter")
    @Operation(summary = "Get all job offers of logged in employer by filter (get with body)", description = "Gets all job offers of logged in employer from database by filter and returns them in list display dto")
    public ResponseEntity<Page<ListDisplayDto>> getAllOffersOfEmployerByFilterEmployerView(@RequestParam(defaultValue = "0") int page,
                                                                                           @RequestParam(defaultValue = "10") int size,
                                                                                           @RequestParam(defaultValue = "1") String order,
                                                                                           @RequestBody JobOfferFilterDto jobOfferFilterDto,
                                                                                           @RequestParam(defaultValue = "null") Boolean isActive) {
        return new ResponseEntity<>(jobOfferListDisplayService.findAllOffersOfEmployerByFilterEmployerView(page, size, order, jobOfferFilterDto, isActive), HttpStatus.OK);
    }

    @GetMapping("job-fairs/{jobFairId}/job-offers/list-display")
    @Operation(summary = "Get all job offers of job fair", description = "Gets all job offers of job fair from database and returns them in list display dto")
    public ResponseEntity<Page<ListDisplayDto>> getAllActiveOffersOfJobFair(@RequestParam(defaultValue = "0") int page,
                                                                            @RequestParam(defaultValue = "10") int size,
                                                                            @RequestParam(defaultValue = "1") String order,
                                                                            @PathVariable Long jobFairId) {
        return new ResponseEntity<>(jobOfferListDisplayService.findAllActiveOffersOfJobFair(page, size, order, jobFairId), HttpStatus.OK);
    }

    @PostMapping("job-fairs/{jobFairId}/job-offers/list-display/filter")
    @Operation(summary = "Get all job offers of job fair by filter (get with body)", description = "Gets all job offers of job fair from database by filter and returns them in list display dto")
    public ResponseEntity<Page<ListDisplayDto>> getAllActiveOffersOfJobFairByFilter(@RequestParam(defaultValue = "0") int page,
                                                                                    @RequestParam(defaultValue = "10") int size,
                                                                                    @RequestParam(defaultValue = "1") String order,
                                                                                    @RequestBody JobOfferFilterDto jobOfferFilterDto,
                                                                                    @PathVariable Long jobFairId) {
        return new ResponseEntity<>(jobOfferListDisplayService.findAllActiveOffersOfJobFairByFilter(page, size, order, jobOfferFilterDto, jobFairId), HttpStatus.OK);
    }

    @GetMapping("employee/job-offers/list-display/saved")
    @Operation(summary = "Get all saved job offers of logged in employee", description = "Gets all saved job offers of logged in employee from database and returns them in list display saved dto")
    public ResponseEntity<Page<ListDisplaySavedDto>> getAllSavedOffersEmployeeView(@RequestParam(defaultValue = "0") int page,
                                                                                   @RequestParam(defaultValue = "10") int size,
                                                                                   @RequestParam(defaultValue = "1") String order) {
        return new ResponseEntity<>(jobOfferListDisplayService.findAllSavedOffersEmployeeView(page, size, order), HttpStatus.OK);
    }

    @PostMapping("employee/job-offers/list-display/saved/filter")
    @Operation(summary = "Get all saved job offers of logged in employee by filter (get with body)", description = "Gets all saved job offers of logged in employee from database by filter and returns them in list display saved dto")
    public ResponseEntity<Page<ListDisplaySavedDto>> getAllSavedOffersByFilterEmployeeView(@RequestParam(defaultValue = "0") int page,
                                                                                           @RequestParam(defaultValue = "10") int size,
                                                                                           @RequestParam(defaultValue = "1") String order,
                                                                                           @RequestBody JobOfferFilterDto jobOfferFilterDto) {
        return new ResponseEntity<>(jobOfferListDisplayService.findAllSavedOffersByFilterEmployeeView(page, size, order, jobOfferFilterDto), HttpStatus.OK);
    }

    @GetMapping("employee/job-offers/list-display")
    @Operation(summary = "Get all job offers for logged in employee", description = "Gets all job offers for from database and returns them in list display saved dto with saved field set to true if job offer is saved by logged in employee")
    public ResponseEntity<Page<ListDisplaySavedDto>> getAllOffersEmployeeView(@RequestParam(defaultValue = "0") int page,
                                                                              @RequestParam(defaultValue = "10") int size,
                                                                              @RequestParam(defaultValue = "1") String order) {
        return new ResponseEntity<>(jobOfferListDisplayService.findAllOffersEmployeeView(page, size, order), HttpStatus.OK);
    }

    @PostMapping("employee/job-offers/list-display/filter")
    @Operation(summary = "Get all job offers for logged in employee by filter (get with body)", description = "Gets all job offers of logged in employee from database by filter and returns them in list display saved dto with saved field set to true if job offer is saved by logged in employee")
    public ResponseEntity<Page<ListDisplaySavedDto>> getAllOffersByFilterEmployeeView(@RequestParam(defaultValue = "0") int page,
                                                                                      @RequestParam(defaultValue = "10") int size,
                                                                                      @RequestParam(defaultValue = "1") String order,
                                                                                      @RequestBody JobOfferFilterDto jobOfferFilterDto) {
        return new ResponseEntity<>(jobOfferListDisplayService.findAllOffersByFilterEmployeeView(page, size, order, jobOfferFilterDto), HttpStatus.OK);
    }

    @GetMapping("employee/job-offers/list-display/employer/{employerId}")
    @Operation(summary = "Get all job offers of employer for logged in employee", description = "Gets all job offers of employer from database and returns them in list display saved dto with saved field set to true if job offer is saved by logged in employee")
    public ResponseEntity<Page<ListDisplaySavedDto>> getAllOffersOfEmployerEmployeeView(@RequestParam(defaultValue = "0") int page,
                                                                                        @RequestParam(defaultValue = "10") int size,
                                                                                        @RequestParam(defaultValue = "1") String order,
                                                                                        @PathVariable Long employerId) {
        return new ResponseEntity<>(jobOfferListDisplayService.findAllOffersOfEmployerEmployeeView(page, size, order, employerId), HttpStatus.OK);
    }

    @PostMapping("employee/job-offers/list-display/employer/{employerId}/filter")
    @Operation(summary = "Get all job offers of employer by filter (get with body) for logged in employee", description = "Gets all job offers of employer from database by filter and returns them in list display saved dto with saved field set to true if job offer is saved by logged in employee")
    public ResponseEntity<Page<ListDisplaySavedDto>> getAllOffersOfEmployerByFilterEmployeeView(@RequestParam(defaultValue = "0") int page,
                                                                                                @RequestParam(defaultValue = "10") int size,
                                                                                                @RequestParam(defaultValue = "1") String order,
                                                                                                @RequestBody JobOfferFilterDto jobOfferFilterDto,
                                                                                                @PathVariable Long employerId) {
        return new ResponseEntity<>(jobOfferListDisplayService.findAllOffersOfEmployerByFilterEmployeeView(page, size, order, jobOfferFilterDto, employerId), HttpStatus.OK);
    }

    @GetMapping("employee/job-offers/list-display/job-fair/{jobFairId}")
    @Operation(summary = "Get all job offers of job fair for logged in employee", description = "Gets all job offers of job fair from database and returns them in list display saved dto with saved field set to true if job offer is saved by logged in employee")
    public ResponseEntity<Page<ListDisplaySavedDto>> getAllOffersOfJobFairEmployeeView(@RequestParam(defaultValue = "0") int page,
                                                                                       @RequestParam(defaultValue = "10") int size,
                                                                                       @RequestParam(defaultValue = "1") String order,
                                                                                       @PathVariable Long jobFairId) {
        return new ResponseEntity<>(jobOfferListDisplayService.findAllOffersOfJobFairEmployeeView(page, size, order, jobFairId), HttpStatus.OK);
    }

    @PostMapping("employee/job-offers/list-display/job-fair/{jobFairId}/filter")
    @Operation(summary = "Get all job offers of job fair by filter (get with body) for logged in employee", description = "Gets all job offers of job fair from database by filter and returns them in list display saved dto with saved field set to true if job offer is saved by logged in employee")
    public ResponseEntity<Page<ListDisplaySavedDto>> getAllOffersOfJobFairByFilterEmployeeView(@RequestParam(defaultValue = "0") int page,
                                                                                               @RequestParam(defaultValue = "10") int size,
                                                                                               @RequestParam(defaultValue = "1") String order,
                                                                                               @RequestBody JobOfferFilterDto jobOfferFilterDto,
                                                                                               @PathVariable Long jobFairId) {
        return new ResponseEntity<>(jobOfferListDisplayService.findAllOffersOfJobFairByFilterEmployeeView(page, size, order, jobOfferFilterDto, jobFairId), HttpStatus.OK);
    }

}
