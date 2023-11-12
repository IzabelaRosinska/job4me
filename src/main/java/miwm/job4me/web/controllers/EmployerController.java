package miwm.job4me.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import miwm.job4me.exceptions.NoSuchElementFoundException;
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
@RequestMapping("employer")
public class EmployerController {

    private final EmployerService employerService;
    private final EmployeeService employeeService;
    private final OrganizerService organizerService;

    public EmployerController(EmployerService employerService, EmployeeService employeeService, OrganizerService organizerService) {
        this.employerService = employerService;
        this.employeeService = employeeService;
        this.organizerService = organizerService;
    }

    @GetMapping("account")
    public ResponseEntity<EmployerDto> getEmployerAccount() {
        EmployerDto employerDto;
        try {
            employerDto = employerService.getEmployerDetails();
        } catch (NoSuchElementFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employerDto, HttpStatus.OK);
    }

    @PostMapping("account")
    public ResponseEntity<EmployerDto> updateEmployerAccount(@RequestBody EmployerDto employerDto) {
        try {
            employerDto = employerService.saveEmployerDetails(employerDto);
        } catch (NoSuchElementFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employerDto, HttpStatus.CREATED);
    }

    @GetMapping("organizer/{id}/account")
    @Operation(summary = "Gets organizer with given id", description = "Gets organizer with given id")
    public ResponseEntity<OrganizerDto> getOrganizerWithIdForEmployer(@PathVariable String id) {
        return new ResponseEntity<>(organizerService.findOrganizerById(Long.valueOf(id)), HttpStatus.OK);
    }

    @GetMapping("employee/{id}/account")
    @Operation(summary = "Gets employee with given id", description = "Gets employee with given id")
    public ResponseEntity<EmployeeDto> getEmployeeWithIdForEmployer(@PathVariable String id) {
        return new ResponseEntity<>(employeeService.findEmployeeById(Long.valueOf(id)), HttpStatus.OK);
    }
}
