package miwm.job4me.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import miwm.job4me.services.users.EmployeeService;
import miwm.job4me.web.model.users.EmployeeDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
    EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee/cv")
    @Operation(summary = "Gets current employee's CV", description = "Gets current employee's data restricted to CV scope")
    public EmployeeDto getCV() {
        return employeeService.findCurrentEmployee();
    }

    @PutMapping("/employee/cv")
    @Operation(summary = "Updates current employee's CV", description = "Updates current employee's data restricted to CV scope")
    public EmployeeDto updateCV(@RequestBody EmployeeDto employeeDto) {
        return employeeService.updateCV(employeeDto);
    }

}
