package miwm.job4me.web.mappers.listDisplay;

import miwm.job4me.model.event.JobFairEmployerParticipation;
import miwm.job4me.model.offer.*;
import miwm.job4me.model.users.Employer;
import miwm.job4me.web.model.listDisplay.ListDisplayDto;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ListDisplayMapper {
    public ListDisplayDto toDtoFromJobFairEmployerParticipation(JobFairEmployerParticipation jobFairEmployerParticipation) {
        ListDisplayDto listDisplayDto = new ListDisplayDto();
        listDisplayDto.setId(jobFairEmployerParticipation.getId());

        Long companyId = jobFairEmployerParticipation.getEmployer().getId();
        String companyName = jobFairEmployerParticipation.getEmployer().getCompanyName();

        String jobFairName = jobFairEmployerParticipation.getJobFair().getName();
        Long jobFairId = jobFairEmployerParticipation.getJobFair().getId();

        listDisplayDto.setName(jobFairName + " - " + companyName);
        listDisplayDto.setDisplayDescription("Zgłoszenie firmy #" + companyId + companyName + " do targów pracy #" + jobFairId + jobFairName);
        listDisplayDto.setPhoto(jobFairEmployerParticipation.getEmployer().getPhoto());

        return listDisplayDto;
    }

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
        StringBuilder displayDescription = new StringBuilder(jobOffer.getEmployer().getCompanyName() + "\n");

        if (jobOffer.getSalaryTo() == null) {
            displayDescription.append(jobOffer.getSalaryFrom()).append(" zł\n");
        } else {
            displayDescription.append(jobOffer.getSalaryFrom()).append(" - ").append(jobOffer.getSalaryTo()).append(" zł\n");
        }

        displayDescription.append(concatLocalizations(jobOffer.getLocalizations())).append("\n");
        displayDescription.append(concatEmploymentForms(jobOffer.getEmploymentForms())).append("\n");
        displayDescription.append(concatContractTypes(jobOffer.getContractTypes())).append("\n");
        displayDescription.append(concatLevels(jobOffer.getLevels())).append("\n");
        displayDescription.append(jobOffer.getWorkingTime()).append("\n");

        return displayDescription.toString();
    }

    private String concatLocalizations(Set<Localization> localizations) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Localization localization : localizations) {
            stringBuilder.append(localization.getCity()).append(", ");
        }

        return stringBuilder.substring(0, stringBuilder.length() - 2);
    }

    private String concatEmploymentForms(Set<EmploymentForm> employmentForms) {
        StringBuilder stringBuilder = new StringBuilder();

        for (EmploymentForm employmentForm : employmentForms) {
            stringBuilder.append(employmentForm.getName()).append(", ");
        }

        return stringBuilder.substring(0, stringBuilder.length() - 2);
    }

    private String concatContractTypes(Set<ContractType> contractTypes) {
        StringBuilder stringBuilder = new StringBuilder();

        for (ContractType contractType : contractTypes) {
            stringBuilder.append(contractType.getName()).append(", ");
        }

        return stringBuilder.substring(0, stringBuilder.length() - 2);
    }

    private String concatLevels(Set<Level> levels) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Level level : levels) {
            stringBuilder.append(level.getName()).append(", ");
        }

        return stringBuilder.substring(0, stringBuilder.length() - 2);
    }
}
