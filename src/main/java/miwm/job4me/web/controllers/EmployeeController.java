package miwm.job4me.web.controllers;

import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.services.users.EmployeeService;
import miwm.job4me.web.model.users.EmployeeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("account")
    public ResponseEntity<EmployeeDto> getEmployeeAccount() {
        EmployeeDto employeeDto;
        try {
            employeeDto = employeeService.getEmployeeDetails();
        } catch (NoSuchElementFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @PostMapping("account")
    public ResponseEntity<EmployeeDto> updateEmployeeAccount(@RequestBody EmployeeDto employeeDto) {
        try {
            employeeDto = employeeService.saveEmployeeDetails(employeeDto);
        } catch (NoSuchElementFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employeeDto, HttpStatus.CREATED);
    }

    @GetMapping("cv")
    @Operation(summary = "Gets current employee's CV", description = "Gets current employee's data restricted to CV scope")
    public EmployeeDto getCV() {
        return employeeService.findCurrentEmployee();
    }

    @PutMapping("cv")
    @Operation(summary = "Updates current employee's CV", description = "Updates current employee's data restricted to CV scope")
    public EmployeeDto updateCV(@RequestBody EmployeeDto employeeDto) {
        return employeeService.updateCV(employeeDto);
    }

}
