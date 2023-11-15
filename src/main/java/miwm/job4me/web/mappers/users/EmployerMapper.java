package miwm.job4me.web.mappers.users;

import miwm.job4me.model.users.Employer;
import miwm.job4me.web.model.users.EmployerDto;
import org.springframework.stereotype.Component;

@Component
public class EmployerMapper {

    public EmployerDto employerToEmployerDto(Employer employer) {
        EmployerDto employerDto = new EmployerDto();
        employerDto.setId(employer.getId());
        employerDto.setCompanyName(employer.getCompanyName());
        employerDto.setEmail(employer.getContactEmail());
        employerDto.setDescription(employer.getDescription());
        employerDto.setDisplayDescription(employer.getDisplayDescription());
        employerDto.setTelephone(employer.getTelephone());
        employerDto.setPhoto(employer.getPhoto());
        employerDto.setAddress(employer.getAddress());
        return employerDto;
    }
}
