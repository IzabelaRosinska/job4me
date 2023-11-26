package miwm.job4me.web.mappers.listDisplay;

import miwm.job4me.model.offer.JobOffer;
import miwm.job4me.model.users.Employer;
import miwm.job4me.web.model.listDisplay.ListDisplayDto;
import miwm.job4me.web.model.listDisplay.ListDisplaySavedDto;
import org.springframework.stereotype.Component;

@Component
public class ListDisplaySavedMapper {
    private final ListDisplayMapper listDisplayMapper;

    public ListDisplaySavedMapper(ListDisplayMapper listDisplayMapper) {
        this.listDisplayMapper = listDisplayMapper;
    }

    public ListDisplaySavedDto toDtoFromEmployer(Employer employer) {
        ListDisplaySavedDto listDisplayDto = new ListDisplaySavedDto();
        listDisplayDto.setId(employer.getId());
        listDisplayDto.setName(employer.getCompanyName());
        listDisplayDto.setDisplayDescription(employer.getDisplayDescription());
        listDisplayDto.setPhoto(employer.getPhoto());
        return listDisplayDto;
    }

    public ListDisplaySavedDto toDtoFromJobOffer(JobOffer jobOffer) {
        ListDisplayDto listDisplayDto = listDisplayMapper.toDtoFromJobOffer(jobOffer);

        ListDisplaySavedDto listDisplaySavedDto = new ListDisplaySavedDto();
        listDisplaySavedDto.setId(listDisplayDto.getId());
        listDisplaySavedDto.setName(listDisplayDto.getName());
        listDisplaySavedDto.setDisplayDescription(listDisplayDto.getDisplayDescription());
        listDisplaySavedDto.setPhoto(listDisplayDto.getPhoto());

        return listDisplaySavedDto;
    }
}
