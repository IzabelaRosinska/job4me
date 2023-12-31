package miwm.job4me.web.controllers.event;

import io.swagger.v3.oas.annotations.Operation;
import miwm.job4me.services.event.JobFairEmployerParticipationService;
import miwm.job4me.web.model.event.JobFairEmployerParticipationDto;
import miwm.job4me.web.model.listDisplay.ListDisplayDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class JobFairEmployerParticipationController {
    private final JobFairEmployerParticipationService jobFairEmployerParticipationService;

    public JobFairEmployerParticipationController(JobFairEmployerParticipationService jobFairEmployerParticipationService) {
        this.jobFairEmployerParticipationService = jobFairEmployerParticipationService;
    }

    @PostMapping("employer/job-fairs/{jobFairId}/employer-participation")
    @Operation(summary = "Create job fair employer participation request", description = "Creates new job fair employer participation in database requested by employer")
    public ResponseEntity<JobFairEmployerParticipationDto> createJobFairEmployerParticipationRequest(@PathVariable Long jobFairId) {
        return new ResponseEntity<>(jobFairEmployerParticipationService.createParticipationRequestByEmployer(jobFairId), HttpStatus.CREATED);
    }

    @PreAuthorize("@jobFairEmployerParticipationController.canEmployerHaveAccessToJobFairEmployerParticipation(#requestId)")
    @GetMapping("employer/employer-participation/{requestId}")
    @Operation(summary = "Get job fair employer participation request", description = "Gets job fair employer participation from database by job fair id")
    public ResponseEntity<JobFairEmployerParticipationDto> getJobFairEmployerParticipationRequestForEmployer(@PathVariable Long requestId) {
        return new ResponseEntity<>(jobFairEmployerParticipationService.findById(requestId), HttpStatus.OK);
    }

    @GetMapping("employer/job-fairs/{jobFairId}/employer-participation/status")
    @Operation(summary = "Get job fair employer participation request for job fair", description = "Gets job fair employer participation request from database by job fair id")
    public ResponseEntity<JobFairEmployerParticipationDto> getJobFairEmployerParticipationRequestStatusForEmployer(@PathVariable Long jobFairId) {
        return new ResponseEntity<>(jobFairEmployerParticipationService.findForEmployerByJobFair(jobFairId), HttpStatus.OK);
    }

    @PreAuthorize("@jobFairEmployerParticipationController.canEmployerHaveAccessToJobFairEmployerParticipation(#requestId)")
    @DeleteMapping("employer/employer-participation/{requestId}")
    @Operation(summary = "Delete job fair employer participation", description = "Deletes job fair employer participation in database")
    public ResponseEntity<Void> deleteJobFairEmployerParticipationForEmployer(@PathVariable Long requestId) {
        jobFairEmployerParticipationService.deleteParticipationRequestByEmployer(requestId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("@jobFairEmployerParticipationController.canOrganizerHaveAccessToJobFairEmployerParticipation(#requestId)")
    @GetMapping("organizer/employer-participation/{requestId}")
    @Operation(summary = "Get job fair employer participation request", description = "Gets job fair employer participation from database by job fair id")
    public ResponseEntity<JobFairEmployerParticipationDto> getJobFairEmployerParticipationRequestForOrganizer(@PathVariable Long requestId) {
        return new ResponseEntity<>(jobFairEmployerParticipationService.findById(requestId), HttpStatus.OK);
    }

    @GetMapping("organizer/employer-participation")
    @Operation(summary = "Get job fair employer participation requests for organizer by status", description = "Gets job fair employer participation requests from database by status")
    public ResponseEntity<Page<ListDisplayDto>> getJobFairEmployerParticipationRequestsByStatusForOrganizer(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) Boolean status,
            @RequestParam(defaultValue = "") String jobFairName,
            @RequestParam(defaultValue = "") String employerCompanyName) {

        Page<ListDisplayDto> jobFairEmployerParticipationDtoPage = jobFairEmployerParticipationService.listDisplayFindAllByOrganizerAndFilters(page, size, status, jobFairName, employerCompanyName);

        if (jobFairEmployerParticipationDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(jobFairEmployerParticipationDtoPage, HttpStatus.OK);
    }


    @PreAuthorize("@jobFairEmployerParticipationController.canOrganizerHaveAccessToJobFair(#jobFairId)")
    @GetMapping("organizer/job-fairs/{jobFairId}/employer-participation")
    @Operation(summary = "Get job fair employer participation requests for organizer by job fair id and status", description = "Gets job fair employer participation requests from database by job fair id and status")
    public ResponseEntity<Page<ListDisplayDto>> getJobFairEmployerParticipationRequestsByJobFairIdAndStatusForOrganizer(
            @PathVariable Long jobFairId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) Boolean status,
            @RequestParam(defaultValue = "") String employerCompanyName) {

        Page<ListDisplayDto> jobFairEmployerParticipationDtoPage = jobFairEmployerParticipationService.listDisplayFindAllByOrganizerAndJobFairAndFilters(page, size, jobFairId, status, employerCompanyName);

        if (jobFairEmployerParticipationDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(jobFairEmployerParticipationDtoPage, HttpStatus.OK);
    }

    @GetMapping("employer/employer-participation")
    @Operation(summary = "Get job fair employer participation requests for employer by status", description = "Gets job fair employer participation requests from database by status")
    public ResponseEntity<Page<ListDisplayDto>> getJobFairEmployerParticipationRequestsByStatusForEmployer(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) Boolean status,
            @RequestParam(defaultValue = "") String jobFairName) {

        Page<ListDisplayDto> jobFairEmployerParticipationDtoPage = jobFairEmployerParticipationService.listDisplayFindAllByEmployerAndFilters(page, size, status, jobFairName);

        if (jobFairEmployerParticipationDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(jobFairEmployerParticipationDtoPage, HttpStatus.OK);
    }

    @PreAuthorize("@jobFairEmployerParticipationController.canOrganizerHaveAccessToJobFairEmployerParticipation(#requestId)")
    @PutMapping("organizer/employer-participation/{requestId}/approval")
    @Operation(summary = "Accept job fair employer participation request", description = "Accepts job fair employer participation request in database")
    public ResponseEntity<JobFairEmployerParticipationDto> acceptJobFairEmployerParticipationRequest(@PathVariable Long requestId) {
        return new ResponseEntity<>(jobFairEmployerParticipationService.acceptParticipationRequestByOrganizer(requestId), HttpStatus.CREATED);
    }

    @PreAuthorize("@jobFairEmployerParticipationController.canOrganizerHaveAccessToJobFairEmployerParticipation(#requestId)")
    @DeleteMapping("organizer/employer-participation/{requestId}/approval")
    @Operation(summary = "Reject job fair employer participation request", description = "Rejects job fair employer participation request in database")
    public ResponseEntity<Void> rejectJobFairEmployerParticipationRequest(@PathVariable Long requestId) {
        jobFairEmployerParticipationService.rejectParticipationRequestByOrganizer(requestId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("@jobFairEmployerParticipationController.canOrganizerHaveAccessToJobFairEmployerParticipation(#requestId)")
    @DeleteMapping("organizer/employer-participation/{requestId}")
    @Operation(summary = "Delete job fair employer participation", description = "Deletes job fair employer participation in database")
    public ResponseEntity<Void> deleteJobFairEmployerParticipation(@PathVariable Long requestId) {
        jobFairEmployerParticipationService.deleteParticipationRequestByOrganizer(requestId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public boolean canEmployerHaveAccessToJobFairEmployerParticipation(Long jobFairEmployerParticipationId) {
        return jobFairEmployerParticipationService.canEmployerHaveAccessToJobFairEmployerParticipation(jobFairEmployerParticipationId);
    }

    public boolean canOrganizerHaveAccessToJobFairEmployerParticipation(Long jobFairEmployerParticipationId) {
        return jobFairEmployerParticipationService.canOrganizerHaveAccessToJobFairEmployerParticipation(jobFairEmployerParticipationId);
    }

    public boolean canOrganizerHaveAccessToJobFair(Long jobFairId) {
        return jobFairEmployerParticipationService.canOrganizerHaveAccessToJobFair(jobFairId);
    }

}
