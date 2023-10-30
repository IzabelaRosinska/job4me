package miwm.job4me.services.users;

import miwm.job4me.model.users.Employee;
import miwm.job4me.model.users.Employer;
import miwm.job4me.services.BaseService;
import miwm.job4me.web.model.users.EmployerDto;

public interface EmployerService  extends BaseService<Employer, Long> {
    EmployerDto getEmployerDetails();
    EmployerDto saveEmployerDetails(EmployerDto employerDto);
}
