package miwm.job4me.web.mappers.offer.parametrs;

import miwm.job4me.model.offer.parameters.EmploymentForm;
import miwm.job4me.web.model.offer.EmploymentFormDto;
import org.springframework.stereotype.Component;

@Component
public class EmploymentFormMapper {
    public EmploymentFormDto toDto(EmploymentForm employmentForm) {
        EmploymentFormDto employmentFormDto = new EmploymentFormDto();
        employmentFormDto.setId(employmentForm.getId());
        employmentFormDto.setName(employmentForm.getName());
        return employmentFormDto;
    }

    public EmploymentForm toEntity(EmploymentFormDto employmentFormDto) {
        EmploymentForm employmentForm = new EmploymentForm();
        employmentForm.setId(employmentFormDto.getId());
        employmentForm.setName(employmentFormDto.getName());
        return employmentForm;
    }
}
