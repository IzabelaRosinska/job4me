package miwm.job4me.web.controllers.event;

import io.swagger.v3.oas.annotations.Operation;
import miwm.job4me.services.event.JobFairEmployerService;
import miwm.job4me.web.model.listDisplay.ListDisplayDto;
import miwm.job4me.web.model.listDisplay.ListDisplaySavedDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobFairEmployerController {
    private final JobFairEmployerService jobFairEmployerService;

    public JobFairEmployerController(JobFairEmployerService jobFairEmployerService) {
        this.jobFairEmployerService = jobFairEmployerService;
    }

    @GetMapping("employee/job-fairs/{jobFairId}/employers")
    @Operation(summary = "Get all employers for job fair", description = "Gets all employers for job fair from database by filters for employee view")
    public ResponseEntity<Page<ListDisplaySavedDto>> getAllEmployersForJobFairEmployeeView(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @PathVariable Long jobFairId,
            @RequestParam(defaultValue = "") String employerCompanyName) {
        Page<ListDisplaySavedDto> listDisplaySavedDtoPage = jobFairEmployerService.findAllEmployersForJobFairEmployeeView(page, size, jobFairId, employerCompanyName);

        if (listDisplaySavedDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(listDisplaySavedDtoPage, HttpStatus.OK);
    }

    @GetMapping("job-fairs/{jobFairId}/employers")
    @Operation(summary = "Get all employers for job fair", description = "Gets all employers for job fair from database by filters")
    public ResponseEntity<Page<ListDisplayDto>> getAllEmployersForJobFair(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @PathVariable Long jobFairId,
            @RequestParam(defaultValue = "") String employerCompanyName) {
        Page<ListDisplayDto> listDisplayDtoPage = jobFairEmployerService.findAllEmployersForJobFairOrganizerView(page, size, jobFairId, employerCompanyName);

        if (listDisplayDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(listDisplayDtoPage, HttpStatus.OK);
    }
}
