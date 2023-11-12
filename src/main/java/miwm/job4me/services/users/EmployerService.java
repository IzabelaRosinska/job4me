package miwm.job4me.services.users;

import com.fasterxml.jackson.databind.JsonNode;
import miwm.job4me.model.users.Employee;
import miwm.job4me.model.users.Employer;
import miwm.job4me.model.users.Person;
import miwm.job4me.services.BaseService;
import miwm.job4me.web.model.users.EmployerDto;
import org.springframework.stereotype.Service;
import miwm.job4me.web.model.users.OrganizerDto;


import java.util.Optional;

public interface EmployerService extends BaseService<Employer, Long> {
    EmployerDto getEmployerDetails();
    EmployerDto saveEmployerDetails(EmployerDto employerDto);

    void saveEmployerDataFromLinkedin(Person user, JsonNode jsonNode);

    Optional<Employer> getEmployerByToken(String token);

    void updatePassword(Employer employer, String password);

    EmployerDto findEmployerById(Long id);

}
