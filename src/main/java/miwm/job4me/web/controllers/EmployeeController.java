package miwm.job4me.web.controllers;

import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.services.users.EmployeeService;
import miwm.job4me.web.model.users.EmployeeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
