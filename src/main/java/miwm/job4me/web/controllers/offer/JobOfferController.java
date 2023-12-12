package miwm.job4me.web.controllers.offer;

import io.swagger.v3.oas.annotations.Operation;
import miwm.job4me.services.offer.JobOfferService;
import miwm.job4me.web.model.offer.JobOfferDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class JobOfferController {
    private final JobOfferService jobOfferService;

    public JobOfferController(JobOfferService jobOfferService) {
        this.jobOfferService = jobOfferService;
    }

    @PostMapping("employer/job-offers")
    @Operation(summary = "Create job offer", description = "Creates new job offer in database")
    public ResponseEntity<JobOfferDto> createJobOffer(@RequestBody JobOfferDto jobOfferDto) {
        return new ResponseEntity<>(jobOfferService.saveDto(jobOfferDto), HttpStatus.CREATED);
    }

    @GetMapping("job-offers")
    @Operation(summary = "Get all job offers", description = "Gets all job offers from database")
    public ResponseEntity<Page<JobOfferDto>> getAllJobOffers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "1") String order) {
        Page<JobOfferDto> jobOfferDtoPage = jobOfferService.findAllByPage(page, size, order);

        if (jobOfferDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(jobOfferDtoPage, HttpStatus.OK);
    }

    @GetMapping("job-offers/{id}")
    @Operation(summary = "Get job offer by id", description = "Gets job offer from database by id")
    public ResponseEntity<JobOfferDto> getJobOfferById(@PathVariable Long id) {
        return new ResponseEntity<>(jobOfferService.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("@jobOfferController.canEmployerHaveAccessToJobOffer(#id)")
    @PutMapping("employer/job-offers/{id}")
    @Operation(summary = "Update job offer", description = "Updates job offer in database")
    public ResponseEntity<JobOfferDto> updateJobOffer(@PathVariable Long id, @RequestBody JobOfferDto jobOfferDto) {
        return new ResponseEntity<>(jobOfferService.update(id, jobOfferDto), HttpStatus.CREATED);
    }

    @PreAuthorize("@jobOfferController.canEmployerHaveAccessToJobOffer(#id)")
    @DeleteMapping("employer/job-offers/{id}")
    @Operation(summary = "Delete job offer", description = "Deletes job offer from database")
    public ResponseEntity<Void> deleteJobOffer(@PathVariable Long id) {
        jobOfferService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("@jobOfferController.canEmployerHaveAccessToJobOffer(#id)")
    @PutMapping("employer/job-offers/{id}/activation")
    @Operation(summary = "Activate job offer", description = "Activates job offer in database")
    public ResponseEntity<JobOfferDto> activateJobOffer(@PathVariable Long id) {
        return new ResponseEntity<>(jobOfferService.activateOffer(id), HttpStatus.OK);
    }

    @PreAuthorize("@jobOfferController.canEmployerHaveAccessToJobOffer(#id)")
    @PutMapping("employer/job-offers/{id}/deactivation")
    @Operation(summary = "Deactivate job offer", description = "Deactivates job offer in database")
    public ResponseEntity<JobOfferDto> deactivateJobOffer(@PathVariable Long id) {
        return new ResponseEntity<>(jobOfferService.deactivateOffer(id), HttpStatus.OK);
    }

    public boolean canEmployerHaveAccessToJobOffer(Long jobOfferId) {
        return jobOfferService.canEmployerHaveAccessToJobOffer(jobOfferId);
    }
}
