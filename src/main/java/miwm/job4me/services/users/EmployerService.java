package miwm.job4me.services.users;

import com.fasterxml.jackson.databind.JsonNode;
import miwm.job4me.model.users.Employee;
import miwm.job4me.model.users.Employer;
import miwm.job4me.model.users.Person;
import miwm.job4me.services.BaseService;
import miwm.job4me.web.model.users.EmployerDto;
import miwm.job4me.web.model.users.OrganizerDto;

public interface EmployerService  extends BaseService<Employer, Long> {
    EmployerDto getEmployerDetails();
    EmployerDto saveEmployerDetails(EmployerDto employerDto);

    void saveEmployerDataFromLinkedin(Person user, JsonNode jsonNode);

    EmployerDto findEmployerById(Long id);
}
