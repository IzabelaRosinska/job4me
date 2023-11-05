package miwm.job4me.services.users;

import com.fasterxml.jackson.databind.JsonNode;
import miwm.job4me.model.users.Employee;
import miwm.job4me.model.users.Person;
import miwm.job4me.services.BaseService;
import miwm.job4me.web.model.users.EmployeeDto;

public interface EmployeeService extends BaseService<Employee, Long> {
    EmployeeDto updateCV(EmployeeDto employeeDto);
    EmployeeDto getEmployeeDetails();
    EmployeeDto saveEmployeeDetails(EmployeeDto employeeDto);

    EmployeeDto findCurrentEmployee();

    void saveEmployeeDataFromLinkedin(Person user, JsonNode jsonNode);

}
