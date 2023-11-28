package miwm.job4me.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import miwm.job4me.services.users.EmployeeService;
import miwm.job4me.services.users.EmployerService;
import miwm.job4me.services.users.OrganizerService;
import miwm.job4me.web.model.users.EmployeeDto;
import miwm.job4me.web.model.users.EmployerDto;
import miwm.job4me.web.model.users.OrganizerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("organizer")
public class OrganizerController {

    private final OrganizerService organizerService;
    private final EmployeeService employeeService;
    private final EmployerService employerService;

    public OrganizerController(OrganizerService organizerService, EmployerService employerService, EmployeeService employeeService) {
        this.organizerService = organizerService;
        this.employeeService = employeeService;
        this.employerService = employerService;
    }

    @GetMapping("account")
    @Operation(summary = "Gets organizer details", description = "Gets organizer account details")
    public ResponseEntity<OrganizerDto> getOrganizerAccount() {
        return new ResponseEntity<>(organizerService.getOrganizerDetails(), HttpStatus.OK);
    }

    @PostMapping("account")
    @Operation(summary = "Save organizer details", description = "Update organizer account")
    public ResponseEntity<OrganizerDto> updateOrganizerAccount(@RequestBody OrganizerDto organizerDto) {
        return new ResponseEntity<>(organizerService.saveOrganizerDetails(organizerDto), HttpStatus.CREATED);
    }

    @GetMapping("employer/{id}/account")
    @Operation(summary = "Gets employer with given id", description = "Gets employer with given id")
    public ResponseEntity<EmployerDto> getEmployerWithIdForOrganizer(@PathVariable String id) {
        return new ResponseEntity<>(employerService.findEmployerById(Long.valueOf(id)), HttpStatus.OK);
    }

    @GetMapping("employee/{id}/account")
    @Operation(summary = "Gets employee with given id", description = "Gets employee with given id")
    public ResponseEntity<EmployeeDto> getEmployeeWithIdForOrganizer(@PathVariable String id) {
        return new ResponseEntity<>(employeeService.findEmployeeById(Long.valueOf(id)), HttpStatus.OK);
    }
}
