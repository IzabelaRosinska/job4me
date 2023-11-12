package miwm.job4me.web.mappers.users;

import miwm.job4me.model.users.Employer;
import miwm.job4me.web.model.users.EmployerDto;
import org.springframework.stereotype.Component;

@Component
public class EmployerMapper {

    public EmployerDto employerToEmployerDto(Employer employer) {
        EmployerDto employerDto = new EmployerDto();
        employerDto.setId(employer.getId().toString());
        employerDto.setCompanyName(employer.getCompanyName());
        employerDto.setEmail(employer.getContactEmail());
        employerDto.setDescription(employer.getDescription());
        employerDto.setDisplayDescription(employer.getDisplayDescription());
        employerDto.setTelephone(employer.getTelephone());
        employerDto.setPhoto(employer.getPhoto());
        employerDto.setAddress(employer.getAddress());
        return employerDto;
    }

    public Employer employerDtoToEmployer(EmployerDto employerDto, Employer employer) {
        employer.setCompanyName(employerDto.getCompanyName());
        employer.setDescription(employerDto.getDescription());
        employer.setDisplayDescription(employerDto.getDisplayDescription());
        employer.setTelephone(employerDto.getTelephone());
        employer.setContactEmail(employerDto.getEmail());
        employer.setPhoto(employerDto.getPhoto());
        employer.setAddress(employerDto.getAddress());
        return employer;
    }
}
