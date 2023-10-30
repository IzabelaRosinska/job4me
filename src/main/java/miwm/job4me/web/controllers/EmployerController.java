package miwm.job4me.web.controllers;

import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.services.users.EmployerService;
import miwm.job4me.web.model.users.EmployerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("employer")
public class EmployerController {

    private final EmployerService employerService;

    public EmployerController(EmployerService employerService) {
        this.employerService = employerService;
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
}
