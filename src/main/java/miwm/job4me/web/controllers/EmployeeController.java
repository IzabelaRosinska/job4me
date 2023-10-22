package miwm.job4me.web.controllers;

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
    public EmployeeDto getCV() {
        return employeeService.findCurrentEmployee();
    }

    @PutMapping("/employee/cv")
    public EmployeeDto updateCV(@RequestBody EmployeeDto employeeDto) {
        return employeeService.updateCV(employeeDto);
    }

}
