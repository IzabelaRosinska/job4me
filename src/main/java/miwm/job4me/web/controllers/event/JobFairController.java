package miwm.job4me.web.controllers.event;

import io.swagger.v3.oas.annotations.Operation;
import miwm.job4me.services.event.JobFairService;
import miwm.job4me.web.model.event.JobFairDto;
import miwm.job4me.web.model.listDisplay.ListDisplayDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class JobFairController {
    private final JobFairService jobFairService;

    public JobFairController(JobFairService jobFairService) {
        this.jobFairService = jobFairService;
    }

    @PostMapping("organizer/job-fairs")
    @Operation(summary = "Create job fair", description = "Creates new job fair in database")
    public ResponseEntity<JobFairDto> createJobFair(@RequestBody JobFairDto jobFairDto) {
        return new ResponseEntity<>(jobFairService.saveDto(jobFairDto), HttpStatus.CREATED);
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
            @RequestParam(defaultValue = "false") Boolean showUpcoming,
            @RequestParam(defaultValue = "") String address) {
        Page<ListDisplayDto> jobFairDtoPage = jobFairService.findAllOfSignedInOrganizerByFiltersListDisplay(page, size, order, showUpcoming, address);

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
        Page<ListDisplayDto> jobFairDtoPage = jobFairService.findAllOfOrganizerByFiltersListDisplay(page, size, order, showUpcoming, address, organizerId);

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

    @PutMapping("organizer/job-fairs/{id}")
    @Operation(summary = "Update job fair", description = "Updates job fair in database")
    public ResponseEntity<JobFairDto> updateJobFair(@PathVariable Long id, @RequestBody JobFairDto jobFairDto) {
        return new ResponseEntity<>(jobFairService.update(id, jobFairDto), HttpStatus.CREATED);
    }

    @DeleteMapping("organizer/job-fairs/{id}")
    @Operation(summary = "Delete job fair", description = "Deletes job fair from database")
    public ResponseEntity<Void> deleteJobFair(@PathVariable Long id) {
        jobFairService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
