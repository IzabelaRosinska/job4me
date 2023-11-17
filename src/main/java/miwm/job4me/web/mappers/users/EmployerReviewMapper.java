package miwm.job4me.web.mappers.users;

import miwm.job4me.model.users.Employer;
import miwm.job4me.web.model.users.EmployerReviewDto;
import org.springframework.stereotype.Component;

@Component
public class EmployerReviewMapper {

    public EmployerReviewDto employerToEmployerReviewDto(Employer employer, Boolean isSaved) {
        EmployerReviewDto employerDto = new EmployerReviewDto();
        employerDto.setId(employer.getId());
        employerDto.setCompanyName(employer.getCompanyName());
        employerDto.setEmail(employer.getContactEmail());
        employerDto.setDescription(employer.getDescription());
        employerDto.setDisplayDescription(employer.getDisplayDescription());
        employerDto.setTelephone(employer.getTelephone());
        employerDto.setPhoto(employer.getPhoto());
        employerDto.setAddress(employer.getAddress());
        employerDto.setIsSaved(isSaved);
        return employerDto;
    }

    public Employer employerReviewDtoToEmployer(EmployerReviewDto employerDto, Employer employer) {
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
