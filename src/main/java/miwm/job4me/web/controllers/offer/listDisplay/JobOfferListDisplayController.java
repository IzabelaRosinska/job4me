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
        Page<ListDisplayDto> listDisplayDtoPage = jobOfferListDisplayService.findAllActiveOffers(page, size, order);

        if (listDisplayDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(listDisplayDtoPage, HttpStatus.OK);
    }

    @PostMapping("job-offers/list-display/filter")
    @Operation(summary = "Get all job offers by filter (get with body)", description = "Gets all job offers from database by filter and returns them in list display dto")
    public ResponseEntity<Page<ListDisplayDto>> getAllActiveOffersByFilter(@RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size,
                                                                           @RequestParam(defaultValue = "1") String order,
                                                                           @RequestBody JobOfferFilterDto jobOfferFilterDto) {
        Page<ListDisplayDto> listDisplayDtoPage = jobOfferListDisplayService.findAllActiveOffersByFilter(page, size, order, jobOfferFilterDto);

        if (listDisplayDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(listDisplayDtoPage, HttpStatus.OK);
    }

    @GetMapping("job-offers/list-display/employer/{employerId}")
    @Operation(summary = "Get all job offers of employer", description = "Gets all job offers of employer from database and returns them in list display dto")
    public ResponseEntity<Page<ListDisplayDto>> getAllActiveOffersOfEmployer(@RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "10") int size,
                                                                             @RequestParam(defaultValue = "1") String order,
                                                                             @PathVariable Long employerId) {
        Page<ListDisplayDto> listDisplayDtoPage = jobOfferListDisplayService.findAllActiveOffersOfEmployer(page, size, order, employerId);

        if (listDisplayDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(listDisplayDtoPage, HttpStatus.OK);
    }

    @PostMapping("job-offers/list-display/employer/{employerId}/filter")
    @Operation(summary = "Get all job offers of employer by filter (get with body)", description = "Gets all job offers of employer from database by filter and returns them in list display dto")
    public ResponseEntity<Page<ListDisplayDto>> getAllActiveOffersOfEmployerByFilter(@RequestParam(defaultValue = "0") int page,
                                                                                     @RequestParam(defaultValue = "10") int size,
                                                                                     @RequestParam(defaultValue = "1") String order,
                                                                                     @RequestBody JobOfferFilterDto jobOfferFilterDto,
                                                                                     @PathVariable Long employerId) {
        Page<ListDisplayDto> listDisplayDtoPage = jobOfferListDisplayService.findAllActiveOffersOfEmployerByFilter(page, size, order, jobOfferFilterDto, employerId);

        if (listDisplayDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(listDisplayDtoPage, HttpStatus.OK);
    }

    @GetMapping("employer/job-offers/list-display")
    @Operation(summary = "Get all job offers of logged in employer", description = "Gets all job offers of logged in  employer from database and returns them in list display dto")
    public ResponseEntity<Page<ListDisplayDto>> getAllOffersOfEmployerEmployerView(@RequestParam(defaultValue = "0") int page,
                                                                                   @RequestParam(defaultValue = "10") int size,
                                                                                   @RequestParam(defaultValue = "1") String order,
                                                                                   @RequestParam(defaultValue = "null") Boolean isActive) {
        Page<ListDisplayDto> listDisplayDtoPage = jobOfferListDisplayService.findAllOffersOfEmployerEmployerView(page, size, order, isActive);

        if (listDisplayDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(listDisplayDtoPage, HttpStatus.OK);
    }

    @PostMapping("employer/job-offers/list-display/filter")
    @Operation(summary = "Get all job offers of logged in employer by filter (get with body)", description = "Gets all job offers of logged in employer from database by filter and returns them in list display dto")
    public ResponseEntity<Page<ListDisplayDto>> getAllOffersOfEmployerByFilterEmployerView(@RequestParam(defaultValue = "0") int page,
                                                                                           @RequestParam(defaultValue = "10") int size,
                                                                                           @RequestParam(defaultValue = "1") String order,
                                                                                           @RequestBody JobOfferFilterDto jobOfferFilterDto,
                                                                                           @RequestParam(defaultValue = "null") Boolean isActive) {
        Page<ListDisplayDto> listDisplayDtoPage = jobOfferListDisplayService.findAllOffersOfEmployerByFilterEmployerView(page, size, order, jobOfferFilterDto, isActive);

        if (listDisplayDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(listDisplayDtoPage, HttpStatus.OK);
    }

    @GetMapping("job-fairs/{jobFairId}/job-offers/list-display")
    @Operation(summary = "Get all job offers of job fair", description = "Gets all job offers of job fair from database and returns them in list display dto")
    public ResponseEntity<Page<ListDisplayDto>> getAllActiveOffersOfJobFair(@RequestParam(defaultValue = "0") int page,
                                                                            @RequestParam(defaultValue = "10") int size,
                                                                            @RequestParam(defaultValue = "1") String order,
                                                                            @PathVariable Long jobFairId) {
        Page<ListDisplayDto> listDisplayDtoPage = jobOfferListDisplayService.findAllActiveOffersOfJobFair(page, size, order, jobFairId);

        if (listDisplayDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(listDisplayDtoPage, HttpStatus.OK);
    }

    @PostMapping("job-fairs/{jobFairId}/job-offers/list-display/filter")
    @Operation(summary = "Get all job offers of job fair by filter (get with body)", description = "Gets all job offers of job fair from database by filter and returns them in list display dto")
    public ResponseEntity<Page<ListDisplayDto>> getAllActiveOffersOfJobFairByFilter(@RequestParam(defaultValue = "0") int page,
                                                                                    @RequestParam(defaultValue = "10") int size,
                                                                                    @RequestParam(defaultValue = "1") String order,
                                                                                    @RequestBody JobOfferFilterDto jobOfferFilterDto,
                                                                                    @PathVariable Long jobFairId) {
        Page<ListDisplayDto> listDisplayDtoPage = jobOfferListDisplayService.findAllActiveOffersOfJobFairByFilter(page, size, order, jobOfferFilterDto, jobFairId);

        if (listDisplayDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(listDisplayDtoPage, HttpStatus.OK);
    }

    @GetMapping("employee/job-offers/list-display")
    @Operation(summary = "Get all job offers for logged in employee", description = "Gets all job offers for from database and returns them in list display saved dto with saved field set to true if job offer is saved by logged in employee")
    public ResponseEntity<Page<ListDisplaySavedDto>> getAllOffersEmployeeView(@RequestParam(defaultValue = "0") int page,
                                                                              @RequestParam(defaultValue = "10") int size,
                                                                              @RequestParam(defaultValue = "1") String order) {
        Page<ListDisplaySavedDto> listDisplaySavedDtoPage = jobOfferListDisplayService.findAllOffersEmployeeView(page, size, order);

        if (listDisplaySavedDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(listDisplaySavedDtoPage, HttpStatus.OK);
    }

    @PostMapping("employee/job-offers/list-display/filter")
    @Operation(summary = "Get all job offers for logged in employee by filter (get with body)", description = "Gets all job offers of logged in employee from database by filter and returns them in list display saved dto with saved field set to true if job offer is saved by logged in employee")
    public ResponseEntity<Page<ListDisplaySavedDto>> getAllOffersByFilterEmployeeView(@RequestParam(defaultValue = "0") int page,
                                                                                      @RequestParam(defaultValue = "10") int size,
                                                                                      @RequestParam(defaultValue = "1") String order,
                                                                                      @RequestBody JobOfferFilterDto jobOfferFilterDto) {
        Page<ListDisplaySavedDto> listDisplaySavedDtoPage = jobOfferListDisplayService.findAllOffersByFilterEmployeeView(page, size, order, jobOfferFilterDto);

        if (listDisplaySavedDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(listDisplaySavedDtoPage, HttpStatus.OK);
    }

    @GetMapping("employee/job-offers/list-display/employer/{employerId}")
    @Operation(summary = "Get all job offers of employer for logged in employee", description = "Gets all job offers of employer from database and returns them in list display saved dto with saved field set to true if job offer is saved by logged in employee")
    public ResponseEntity<Page<ListDisplaySavedDto>> getAllOffersOfEmployerEmployeeView(@RequestParam(defaultValue = "0") int page,
                                                                                        @RequestParam(defaultValue = "10") int size,
                                                                                        @RequestParam(defaultValue = "1") String order,
                                                                                        @PathVariable Long employerId) {
        Page<ListDisplaySavedDto> listDisplaySavedDtoPage = jobOfferListDisplayService.findAllOffersOfEmployerEmployeeView(page, size, order, employerId);

        if (listDisplaySavedDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(listDisplaySavedDtoPage, HttpStatus.OK);
    }

    @PostMapping("employee/job-offers/list-display/employer/{employerId}/filter")
    @Operation(summary = "Get all job offers of employer by filter (get with body) for logged in employee", description = "Gets all job offers of employer from database by filter and returns them in list display saved dto with saved field set to true if job offer is saved by logged in employee")
    public ResponseEntity<Page<ListDisplaySavedDto>> getAllOffersOfEmployerByFilterEmployeeView(@RequestParam(defaultValue = "0") int page,
                                                                                                @RequestParam(defaultValue = "10") int size,
                                                                                                @RequestParam(defaultValue = "1") String order,
                                                                                                @RequestBody JobOfferFilterDto jobOfferFilterDto,
                                                                                                @PathVariable Long employerId) {
        Page<ListDisplaySavedDto> listDisplaySavedDtoPage = jobOfferListDisplayService.findAllOffersOfEmployerByFilterEmployeeView(page, size, order, jobOfferFilterDto, employerId);

        if (listDisplaySavedDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(listDisplaySavedDtoPage, HttpStatus.OK);
    }

    @GetMapping("employee/job-offers/list-display/job-fairs/{jobFairId}")
    @Operation(summary = "Get all job offers of job fair for logged in employee", description = "Gets all job offers of job fair from database and returns them in list display saved dto with saved field set to true if job offer is saved by logged in employee")
    public ResponseEntity<Page<ListDisplaySavedDto>> getAllOffersOfJobFairEmployeeView(@RequestParam(defaultValue = "0") int page,
                                                                                       @RequestParam(defaultValue = "10") int size,
                                                                                       @RequestParam(defaultValue = "1") String order,
                                                                                       @PathVariable Long jobFairId) {
        Page<ListDisplaySavedDto> listDisplaySavedDtoPage = jobOfferListDisplayService.findAllOffersOfJobFairEmployeeView(page, size, order, jobFairId);

        if (listDisplaySavedDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(listDisplaySavedDtoPage, HttpStatus.OK);
    }

    @PostMapping("employee/job-offers/list-display/job-fairs/{jobFairId}/filter")
    @Operation(summary = "Get all job offers of job fair by filter (get with body) for logged in employee", description = "Gets all job offers of job fair from database by filter and returns them in list display saved dto with saved field set to true if job offer is saved by logged in employee")
    public ResponseEntity<Page<ListDisplaySavedDto>> getAllOffersOfJobFairByFilterEmployeeView(@RequestParam(defaultValue = "0") int page,
                                                                                               @RequestParam(defaultValue = "10") int size,
                                                                                               @RequestParam(defaultValue = "1") String order,
                                                                                               @RequestBody JobOfferFilterDto jobOfferFilterDto,
                                                                                               @PathVariable Long jobFairId) {
        Page<ListDisplaySavedDto> listDisplaySavedDtoPage = jobOfferListDisplayService.findAllOffersOfJobFairByFilterEmployeeView(page, size, order, jobOfferFilterDto, jobFairId);

        if (listDisplaySavedDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(listDisplaySavedDtoPage, HttpStatus.OK);
    }

}
