package miwm.job4me.web.mappers.listDisplay;

import miwm.job4me.model.users.Employer;
import miwm.job4me.web.model.listDisplay.ListDisplaySavedDto;
import org.springframework.stereotype.Component;

@Component
public class ListDisplaySavedMapper {

    public ListDisplaySavedDto toDtoFromEmployer(Employer employer) {
        ListDisplaySavedDto listDisplayDto = new ListDisplaySavedDto();
        listDisplayDto.setId(employer.getId());
        listDisplayDto.setName(employer.getCompanyName());
        listDisplayDto.setDisplayDescription(employer.getDisplayDescription());
        listDisplayDto.setPhoto(employer.getPhoto());
        return listDisplayDto;
    }
}
