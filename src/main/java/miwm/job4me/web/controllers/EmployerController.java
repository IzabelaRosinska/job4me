package miwm.job4me.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import miwm.job4me.services.users.EmployeeService;
import miwm.job4me.services.users.EmployerService;
import miwm.job4me.services.users.OrganizerService;
import miwm.job4me.services.users.SavedEmployeeService;
import miwm.job4me.web.model.listDisplay.ListDisplayDto;
import miwm.job4me.web.model.users.EmployeeReviewDto;
import miwm.job4me.web.model.users.EmployerDto;
import miwm.job4me.web.model.users.OrganizerDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employer")
public class EmployerController {

    private final EmployerService employerService;
    private final EmployeeService employeeService;
    private final SavedEmployeeService savedEmployeeService;
    private final OrganizerService organizerService;

    public EmployerController(EmployerService employerService, EmployeeService employeeService, SavedEmployeeService savedEmployeeService, OrganizerService organizerService) {
        this.employerService = employerService;
        this.employeeService = employeeService;
        this.savedEmployeeService = savedEmployeeService;
        this.organizerService = organizerService;
    }

    @GetMapping("account")
    @Operation(summary = "Gets employer details", description = "Gets employer account details")
    public ResponseEntity<EmployerDto> getEmployerAccount() {
        return new ResponseEntity<>(employerService.getEmployerDetails(), HttpStatus.OK);
    }

    @PostMapping("account")
    @Operation(summary = "Save employer details", description = "Update employer account")
    public ResponseEntity<EmployerDto> updateEmployerAccount(@RequestBody EmployerDto employerDto) {
        return new ResponseEntity<>(employerService.saveEmployerDetails(employerDto), HttpStatus.CREATED);
    }

    @GetMapping("organizer/{id}/account")
    @Operation(summary = "Gets organizer with given id", description = "Gets organizer with given id")
    public ResponseEntity<OrganizerDto> getOrganizerWithIdForEmployer(@PathVariable Long id) {
        return new ResponseEntity<>(organizerService.findOrganizerById(id), HttpStatus.OK);
    }

    @GetMapping("employee/{id}/account")
    @Operation(summary = "Gets employee with given id", description = "Gets employee with given id")
    public ResponseEntity<EmployeeReviewDto> getEmployeeWithIdForEmployer(@PathVariable Long id) {
        return new ResponseEntity<>(savedEmployeeService.findEmployeeWithIdByUser(id), HttpStatus.OK);
    }

    @PostMapping("employee/{id}")
    public ResponseEntity<?> saveEmployeeForEmployer(@PathVariable Long id) {
        savedEmployeeService.addEmployeeToSaved(id);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @DeleteMapping("employee/{id}")
    public ResponseEntity<?> deleteEmployeeForEmployer(@PathVariable Long id) {
        savedEmployeeService.deleteEmployeeFromSaved(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("employees")
    public ResponseEntity<List<EmployeeReviewDto>> getSavedEmployees() {
        List<EmployeeReviewDto> employees = savedEmployeeService.getSavedEmployees();
        return new ResponseEntity<>(employees, HttpStatus.CREATED);
    }

    @GetMapping("employees/list-display")
    @Operation(summary = "Gets all saved employees in paginated list display form", description = "Gets all saved employees of logged in employer in paginated list display form")
    public ResponseEntity<Page<ListDisplayDto>> getAllSavedEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<ListDisplayDto> employeeDtoPage = savedEmployeeService.getSavedEmployeesForEmployerWithIdListDisplay(page, size);

        if (employeeDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(employeeDtoPage, HttpStatus.OK);
    }
}
