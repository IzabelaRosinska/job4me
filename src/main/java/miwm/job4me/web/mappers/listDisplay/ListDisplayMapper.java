package miwm.job4me.web.mappers.listDisplay;

import miwm.job4me.model.offer.JobOffer;
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

    public ListDisplayDto toDtoFromJobOffer(JobOffer jobOffer) {
        ListDisplayDto listDisplayDto = new ListDisplayDto();
        listDisplayDto.setId(jobOffer.getId());
        listDisplayDto.setName(jobOffer.getOfferName());
        listDisplayDto.setDisplayDescription(createJobOfferDisplay(jobOffer));
        listDisplayDto.setPhoto(jobOffer.getEmployer().getPhoto());
        return listDisplayDto;
    }

    private String createJobOfferDisplay(JobOffer jobOffer) {
        String displayDescription = jobOffer.getEmployer().getCompanyName() + "\n";

        if (jobOffer.getSalaryTo() == null) {
            displayDescription += jobOffer.getSalaryFrom() + " zł\n";
        } else {
            displayDescription += jobOffer.getSalaryFrom() + " - " + jobOffer.getSalaryTo() + " zł\n";
        }

        for (int i = 0; i < jobOffer.getLocalizations().size(); i++) {
            displayDescription += jobOffer.getLocalizations().toArray()[i] + ", ";
        }

        displayDescription = displayDescription.substring(0, displayDescription.length() - 2) + "\n";

        for (int i = 0; i < jobOffer.getEmploymentForms().size(); i++) {
            displayDescription += jobOffer.getEmploymentForms().toArray()[i] + ", ";
        }

        displayDescription = displayDescription.substring(0, displayDescription.length() - 2) + "\n";

        for (int i = 0; i < jobOffer.getLevels().size(); i++) {
            displayDescription += jobOffer.getLevels().toArray()[i] + ", ";
        }

        displayDescription = displayDescription.substring(0, displayDescription.length() - 2) + "\n";

        return displayDescription;
    }
}
