package miwm.job4me.web.controllers.event;

import io.swagger.v3.oas.annotations.Operation;
import miwm.job4me.services.event.JobFairService;
import miwm.job4me.web.model.event.JobFairDto;
import miwm.job4me.web.model.listDisplay.ListDisplayDto;
import miwm.job4me.web.model.payment.PaymentCheckout;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class JobFairController {
    private final JobFairService jobFairService;

    public JobFairController(JobFairService jobFairService) {
        this.jobFairService = jobFairService;
    }


    @GetMapping("job-fairs")
    @Operation(summary = "Get all job fairs by filters", description = "Gets all job fairs from database by filters")
    public ResponseEntity<Page<ListDisplayDto>> getAllJobFairs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "1") String order,
            @RequestParam(defaultValue = "false") Boolean showUpcoming,
            @RequestParam(defaultValue = "") String address) {
        Page<ListDisplayDto> jobFairDtoPage = jobFairService.findAllByFiltersListDisplay(page, size, order, showUpcoming, address);

        if (jobFairDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(jobFairDtoPage, HttpStatus.OK);
    }

    @GetMapping("organizer/job-fairs")
    @Operation(summary = "Get all job fairs of signed in organizer by filters", description = "Gets all job fairs of signed in organizer from database by filters")
    public ResponseEntity<Page<ListDisplayDto>> getAllSignedInOrganizerJobFairs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "1") String order,
            @RequestParam(required = false) Boolean showUpcoming,
            @RequestParam(defaultValue = "") String address,
            @RequestParam(required = false) Boolean isPaid) {
        Page<ListDisplayDto> jobFairDtoPage = jobFairService.findAllOfSignedInOrganizerByFiltersListDisplay(page, size, order, showUpcoming, address, isPaid);

        if (jobFairDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(jobFairDtoPage, HttpStatus.OK);
    }

    @GetMapping("job-fairs/organizer/{organizerId}")
    @Operation(summary = "Get all job fairs of organizer by filters", description = "Gets all job fairs of organizer from database by filters")
    public ResponseEntity<Page<ListDisplayDto>> getAllOrganizerJobFairs(
            @PathVariable Long organizerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "1") String order,
            @RequestParam(defaultValue = "false") Boolean showUpcoming,
            @RequestParam(defaultValue = "") String address) {
        Page<ListDisplayDto> jobFairDtoPage = jobFairService.findAllOfOrganizerByFiltersListDisplay(page, size, order, showUpcoming, address, organizerId, true);

        if (jobFairDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(jobFairDtoPage, HttpStatus.OK);
    }

    @GetMapping("job-fairs/{id}")
    @Operation(summary = "Get job fair by id", description = "Gets job fair from database by id")
    public ResponseEntity<JobFairDto> getJobFairById(@PathVariable Long id) {
        return new ResponseEntity<>(jobFairService.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("@jobFairController.canOrganizerHaveAccessToJobFair(#id)")
    @PutMapping("organizer/job-fairs/{id}")
    @Operation(summary = "Update job fair", description = "Updates job fair in database")
    public ResponseEntity<JobFairDto> updateJobFair(@PathVariable Long id, @RequestBody JobFairDto jobFairDto) {
        return new ResponseEntity<>(jobFairService.update(id, jobFairDto), HttpStatus.CREATED);
    }

    @PreAuthorize("@jobFairController.canOrganizerHaveAccessToJobFair(#id)")
    @DeleteMapping("organizer/job-fairs/{id}")
    @Operation(summary = "Delete job fair", description = "Deletes job fair from database")
    public ResponseEntity<Void> deleteJobFair(@PathVariable Long id) {
        jobFairService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("organizer/job-fairs/payment")
    @Operation(summary = "Create JobFair and payment session", description = "Create JobFair and payment session")
    public ResponseEntity<PaymentCheckout> payForOrganizerAccount(@RequestBody JobFairDto jobFairDto) {
        PaymentCheckout paymentCheckout = jobFairService.coordinateJobFairPayment(jobFairDto);

        return new ResponseEntity<>(paymentCheckout, HttpStatus.OK);
    }

    @GetMapping("organizer/job-fairs/{jobFairId}/access")
    @Operation(summary = "Check if job fair is created by organizer", description = "Check if job fair is created by organizer")
    public ResponseEntity<Boolean> isJobFairCreatedByJobFairId(@PathVariable Long jobFairId) {
        return new ResponseEntity<>(jobFairService.isJobFairCreatedByOrganizer(jobFairId), HttpStatus.OK);
    }

    public boolean canOrganizerHaveAccessToJobFair(Long jobFairId) {
        return jobFairService.isJobFairCreatedByOrganizer(jobFairId);
    }

}
