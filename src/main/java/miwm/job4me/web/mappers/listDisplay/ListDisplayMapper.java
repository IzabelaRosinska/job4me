package miwm.job4me.web.mappers.listDisplay;

import miwm.job4me.model.users.Employer;
import miwm.job4me.web.model.listDisplay.ListDisplayDto;
import org.springframework.stereotype.Component;

@Component
public class ListDisplayMapper {

    public ListDisplayDto toDtoFromEmployer(Employer employer) {
        ListDisplayDto listDisplayDto = new ListDisplayDto();
        listDisplayDto.setId(employer.getId());
        listDisplayDto.setName(employer.getCompanyName());
        listDisplayDto.setDisplayDescription(employer.getDisplayDescription());
        listDisplayDto.setPhoto(employer.getPhoto());
        return listDisplayDto;
    }
}
