package miwm.job4me.web.controllers.event;

import io.swagger.v3.oas.annotations.Operation;
import miwm.job4me.services.event.JobFairService;
import miwm.job4me.web.model.event.JobFairDto;
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

    @PostMapping("job-fairs")
    @Operation(summary = "Create job fair", description = "Creates new job fair in database")
    public ResponseEntity<JobFairDto> createJobFair(@RequestBody JobFairDto jobFairDto) {
        return new ResponseEntity<>(jobFairService.saveDto(jobFairDto), HttpStatus.CREATED);
    }

    @GetMapping("job-fairs")
    @Operation(summary = "Get all job fairs by filters", description = "Gets all job fairs from database by filters")
    public ResponseEntity<Page<JobFairDto>> getAllJobFairs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<JobFairDto> jobFairDtoPage = jobFairService.findAllByFilters(page, size);

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

    @PutMapping("job-fairs/{id}")
    @Operation(summary = "Update job fair", description = "Updates job fair in database")
    public ResponseEntity<JobFairDto> updateJobFair(@PathVariable Long id, @RequestBody JobFairDto jobFairDto) {
        return new ResponseEntity<>(jobFairService.update(id, jobFairDto), HttpStatus.CREATED);
    }

    @DeleteMapping("job-fairs/{id}")
    @Operation(summary = "Delete job fair", description = "Deletes job fair from database")
    public ResponseEntity<Void> deleteJobFair(@PathVariable Long id) {
        jobFairService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}