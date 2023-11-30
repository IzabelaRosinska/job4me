package miwm.job4me.web.mappers.listDisplay;

import miwm.job4me.model.event.JobFair;
import miwm.job4me.model.event.JobFairEmployerParticipation;
import miwm.job4me.model.offer.JobOffer;
import miwm.job4me.model.users.Employee;
import miwm.job4me.model.users.Employer;
import miwm.job4me.web.mappers.offer.JobOfferMapper;
import miwm.job4me.web.model.listDisplay.ListDisplayDto;
import miwm.job4me.web.model.offer.JobOfferDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListDisplayMapper {
    private JobOfferMapper jobOfferMapper;

    public ListDisplayMapper(JobOfferMapper jobOfferMapper) {
        this.jobOfferMapper = jobOfferMapper;
    }

    public ListDisplayDto toDtoFromJobFair(JobFair jobFair) {
        ListDisplayDto listDisplayDto = new ListDisplayDto();
        listDisplayDto.setId(jobFair.getId());
        listDisplayDto.setName(jobFair.getName());

        String displayDescription = jobFair.getDateStart() + " - " + jobFair.getDateEnd() + "\n" + jobFair.getAddress() + "\n" + jobFair.getDisplayDescription();

        listDisplayDto.setDisplayDescription(displayDescription);
        listDisplayDto.setPhoto(jobFair.getPhoto());
        return listDisplayDto;
    }

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

    public ListDisplayDto toDtoFromEmployee(Employee employee) {
        ListDisplayDto listDisplayDto = new ListDisplayDto();
        listDisplayDto.setId(employee.getId());
        listDisplayDto.setName(employee.getFirstName() + " " + employee.getLastName());

        if (employee.getTelephone() == null || employee.getTelephone().equals("")) {
            listDisplayDto.setDisplayDescription(employee.getContactEmail());
        } else {
            listDisplayDto.setDisplayDescription(employee.getContactEmail() + "\n" + employee.getTelephone());
        }

        listDisplayDto.setPhoto("");
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
        JobOfferDto jobOfferDto = jobOfferMapper.toDto(jobOffer);
        StringBuilder displayDescription = new StringBuilder(jobOffer.getEmployer().getCompanyName() + "\n");

        if (jobOffer.getSalaryTo() == null) {
            displayDescription.append(jobOffer.getSalaryFrom()).append(" zł\n");
        } else {
            displayDescription.append(jobOffer.getSalaryFrom()).append(" - ").append(jobOffer.getSalaryTo()).append(" zł\n");
        }

        displayDescription.append(concatListString(jobOfferDto.getLocalizations())).append("\n");
        displayDescription.append(concatListString(jobOfferDto.getEmploymentForms())).append("\n");
        displayDescription.append(concatListString(jobOfferDto.getContractTypes())).append("\n");
        displayDescription.append(concatListString(jobOfferDto.getLevels())).append("\n");

        return displayDescription.toString();
    }

    private String concatListString(List<String> list) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String element : list) {
            stringBuilder.append(element).append(", ");
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());

        return stringBuilder.toString();
    }
}
