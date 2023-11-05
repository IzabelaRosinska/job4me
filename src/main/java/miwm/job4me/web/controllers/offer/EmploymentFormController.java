package miwm.job4me.web.controllers.offer;

import io.swagger.v3.oas.annotations.Operation;
import miwm.job4me.services.offer.EmploymentFormService;
import miwm.job4me.web.model.offer.EmploymentFormDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmploymentFormController {
    private final EmploymentFormService employmentFormService;

    public EmploymentFormController(EmploymentFormService employmentFormService) {
        this.employmentFormService = employmentFormService;
    }

    @PostMapping("employment-forms")
    @Operation(summary = "Create employment form", description = "Creates new employment form in database")
    public ResponseEntity<EmploymentFormDto> createEmploymentForm(@RequestBody EmploymentFormDto employmentFormDto) {
        return new ResponseEntity<>(employmentFormService.saveDto(employmentFormDto), HttpStatus.CREATED);
    }

    @GetMapping("employment-forms")
    @Operation(summary = "Get all employment forms with pagination and filter", description = "Gets all employment forms from database with pagination and filter (by name)")
    public ResponseEntity<Page<EmploymentFormDto>> getAllEmploymentForms(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<EmploymentFormDto> employmentFormDtoPage = employmentFormService.findByNameContaining(page, size, name);

        if (employmentFormDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(employmentFormDtoPage, HttpStatus.OK);
    }

    @GetMapping("employment-forms/{id}")
    @Operation(summary = "Get employment form by id", description = "Gets employment form from database by id")
    public ResponseEntity<EmploymentFormDto> getEmploymentFormById(@PathVariable Long id) {
        return new ResponseEntity<>(employmentFormService.findById(id), HttpStatus.OK);
    }

    @PutMapping("employment-forms/{id}")
    @Operation(summary = "Update employment form", description = "Updates employment form in database")
    public ResponseEntity<EmploymentFormDto> updateEmploymentForm(@PathVariable Long id, @RequestBody EmploymentFormDto employmentFormDto) {
        return new ResponseEntity<>(employmentFormService.update(id, employmentFormDto), HttpStatus.CREATED);
    }

    @DeleteMapping("employment-forms/{id}")
    @Operation(summary = "Delete employment form", description = "Deletes employment form from database")
    public ResponseEntity<Void> deleteEmploymentForm(@PathVariable Long id) {
        employmentFormService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
