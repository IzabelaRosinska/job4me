package miwm.job4me.web.controllers.event;

import io.swagger.v3.oas.annotations.Operation;
import miwm.job4me.services.event.JobFairEmployerParticipationService;
import miwm.job4me.web.model.event.JobFairEmployerParticipationDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("employer/employer-participation/{requestId}")
    @Operation(summary = "Get job fair employer participation request", description = "Gets job fair employer participation from database by job fair id")
    public ResponseEntity<JobFairEmployerParticipationDto> getJobFairEmployerParticipationRequestForEmployer(@PathVariable Long requestId) {
        return new ResponseEntity<>(jobFairEmployerParticipationService.findById(requestId), HttpStatus.OK);
    }

    @GetMapping("organizer/employer-participation/{requestId}")
    @Operation(summary = "Get job fair employer participation request", description = "Gets job fair employer participation from database by job fair id")
    public ResponseEntity<JobFairEmployerParticipationDto> getJobFairEmployerParticipationRequestForOrganizer(@PathVariable Long requestId) {
        return new ResponseEntity<>(jobFairEmployerParticipationService.findById(requestId), HttpStatus.OK);
    }

    @GetMapping("organizer/employer-participation")
    @Operation(summary = "Get job fair employer participation requests for organizer by status", description = "Gets job fair employer participation requests from database by status")
    public ResponseEntity<Page<JobFairEmployerParticipationDto>> getJobFairEmployerParticipationRequestsByStatusForOrganizer(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "") String status) {

        Page<JobFairEmployerParticipationDto> jobFairEmployerParticipationDtoPage = jobFairEmployerParticipationService.findAllByOrganizerAndFilters(page, size);

        if (jobFairEmployerParticipationDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(jobFairEmployerParticipationDtoPage, HttpStatus.OK);
    }

    @GetMapping("organizer/job-fairs/{jobFairId}/employer-participation")
    @Operation(summary = "Get job fair employer participation requests for organizer by job fair id and status", description = "Gets job fair employer participation requests from database by job fair id and status")
    public ResponseEntity<Page<JobFairEmployerParticipationDto>> getJobFairEmployerParticipationRequestsByJobFairIdAndStatusForOrganizer(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @PathVariable Long jobFairId,
            @RequestParam(defaultValue = "") String status) {

        Page<JobFairEmployerParticipationDto> jobFairEmployerParticipationDtoPage = jobFairEmployerParticipationService.findAllByOrganizerAndJobFairAndFilters(page, size, jobFairId);

        if (jobFairEmployerParticipationDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(jobFairEmployerParticipationDtoPage, HttpStatus.OK);
    }

    @GetMapping("employer/employer-participation")
    @Operation(summary = "Get job fair employer participation requests for employer by status", description = "Gets job fair employer participation requests from database by status")
    public ResponseEntity<Page<JobFairEmployerParticipationDto>> getJobFairEmployerParticipationRequestsByStatusForEmployer(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "") String status) {

        Page<JobFairEmployerParticipationDto> jobFairEmployerParticipationDtoPage = jobFairEmployerParticipationService.findAllByEmployerAndFilters(page, size);

        if (jobFairEmployerParticipationDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(jobFairEmployerParticipationDtoPage, HttpStatus.OK);
    }

    @PutMapping("organizer/employer-participation/{requestId}/accept")
    @Operation(summary = "Accept job fair employer participation request", description = "Accepts job fair employer participation request in database")
    public ResponseEntity<JobFairEmployerParticipationDto> acceptJobFairEmployerParticipationRequest(@PathVariable Long requestId) {
        return new ResponseEntity<>(jobFairEmployerParticipationService.acceptParticipationRequestByOrganizer(requestId), HttpStatus.CREATED);
    }

    @DeleteMapping("organizer/employer-participation/{requestId}/reject")
    @Operation(summary = "Reject job fair employer participation request", description = "Rejects job fair employer participation request in database")
    public ResponseEntity<Void> rejectJobFairEmployerParticipationRequest(@PathVariable Long requestId) {
        jobFairEmployerParticipationService.rejectParticipationRequestByOrganizer(requestId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("organizer/employer-participation/{requestId}")
    @Operation(summary = "Delete job fair employer participation", description = "Deletes job fair employer participation in database")
    public ResponseEntity<Void> deleteJobFairEmployerParticipation(@PathVariable Long requestId) {
        jobFairEmployerParticipationService.deleteParticipationRequestByOrganizer(requestId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
