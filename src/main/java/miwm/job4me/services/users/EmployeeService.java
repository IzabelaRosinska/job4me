package miwm.job4me.services.users;

import miwm.job4me.model.users.Employee;
import miwm.job4me.services.BaseService;
import miwm.job4me.web.model.users.EmployeeDto;

public interface EmployeeService extends BaseService<Employee, Long> {
    EmployeeDto updateCV(EmployeeDto employeeDto);

}
