package miwm.job4me.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import miwm.job4me.services.users.EmployerService;
import miwm.job4me.services.users.OrganizerService;
import miwm.job4me.web.model.users.EmployerDto;
import miwm.job4me.web.model.users.OrganizerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GuestController {

    private final EmployerService employerService;
    private final OrganizerService organizerService;

    public GuestController(EmployerService employerService, OrganizerService organizerService) {
        this.employerService = employerService;
        this.organizerService = organizerService;
    }

    @GetMapping("/account/employer/{id}")
    @Operation(summary = "Gets employer with given id", description = "Gets employer with given id for not logged user")
    public ResponseEntity<EmployerDto> getEmployer(@PathVariable Long id) {
        return new ResponseEntity<>(employerService.findEmployerById(id), HttpStatus.OK);
    }

    @GetMapping("/account/organizer/{id}")
    @Operation(summary = "Gets organizer with given id", description = "Gets organizer with given id for not logged user")
    public ResponseEntity<OrganizerDto> getOrganizer(@PathVariable Long id) {
        return new ResponseEntity<>(organizerService.findOrganizerById(id), HttpStatus.OK);
    }
}
