package miwm.job4me.web.mappers.offer;

import miwm.job4me.model.offer.JobOffer;
import miwm.job4me.model.users.Employer;
import miwm.job4me.web.model.offer.JobOfferReviewDto;
import org.springframework.stereotype.Component;

@Component
public class JobOfferReviewMapper {

    private final JobOfferMapper jobOfferMapper;

    public JobOfferReviewMapper(JobOfferMapper jobOfferMapper) {
        this.jobOfferMapper = jobOfferMapper;
    }


    public JobOfferReviewDto jobOfferToJobOfferDto(JobOffer jobOffer, Boolean isSaved) {
        JobOfferReviewDto jobOfferDto = new JobOfferReviewDto();
        jobOfferDto.setId(jobOffer.getId());
        jobOfferDto.setOfferName(jobOffer.getOfferName());
        jobOfferDto.setEmployerId(jobOffer.getEmployer().getId());
        jobOfferDto.setIndustries(jobOfferMapper.industriesSetToStringList(jobOffer.getIndustries()));
        jobOfferDto.setLocalizations(jobOfferMapper.localizationsSetToStringList(jobOffer.getLocalizations()));
        jobOfferDto.setEmploymentForms(jobOfferMapper.employmentFormsSetToStringList(jobOffer.getEmploymentForms()));
        jobOfferDto.setSalaryFrom(jobOffer.getSalaryFrom());
        jobOfferDto.setSalaryTo(jobOffer.getSalaryTo());
        jobOfferDto.setContractTypes(jobOfferMapper.contractTypesSetToStringList(jobOffer.getContractTypes()));
        jobOfferDto.setWorkingTime(jobOffer.getWorkingTime());
        jobOfferDto.setLevels(jobOfferMapper.levelsSetToStringList(jobOffer.getLevels()));
        jobOfferDto.setRequirements(jobOfferMapper.requirementsSetToStringList(jobOffer.getRequirements()));
        jobOfferDto.setExtraSkills(jobOfferMapper.extraSkillsSetToStringList(jobOffer.getExtraSkills()));
        jobOfferDto.setDuties(jobOffer.getDuties());
        jobOfferDto.setDescription(jobOffer.getDescription());
        jobOfferDto.setIsSaved(isSaved);

        return jobOfferDto;
    }

    public JobOffer jobOfferDtoToJobOffer(JobOfferReviewDto jobOfferDto) {
        JobOffer jobOffer = new JobOffer();
        jobOffer.setOfferName(jobOfferDto.getOfferName());
        jobOffer.setEmployer(Employer.builder().id(jobOfferDto.getEmployerId()).build());
        jobOffer.setIndustries(jobOfferMapper.stringListToIndustriesSet(jobOfferDto.getIndustries()));
        jobOffer.setLocalizations(jobOfferMapper.stringListToLocalizationsSet(jobOfferDto.getLocalizations()));
        jobOffer.setEmploymentForms(jobOfferMapper.stringListToEmploymentFormsSet(jobOfferDto.getEmploymentForms()));
        jobOffer.setSalaryFrom(jobOfferDto.getSalaryFrom());
        jobOffer.setSalaryTo(jobOfferDto.getSalaryTo());
        jobOffer.setContractTypes(jobOfferMapper.stringListToContractTypesSet(jobOfferDto.getContractTypes()));
        jobOffer.setWorkingTime(jobOfferDto.getWorkingTime());
        jobOffer.setLevels(jobOfferMapper.stringListToLevelsSet(jobOfferDto.getLevels()));
        jobOffer.setRequirements(jobOfferMapper.stringListToRequirementsSet(jobOfferDto.getRequirements(), jobOffer));
        jobOffer.setExtraSkills(jobOfferMapper.stringListToExtraSkillsSet(jobOfferDto.getExtraSkills(), jobOffer));
        jobOffer.setDuties(jobOfferDto.getDuties());
        jobOffer.setDescription(jobOfferDto.getDescription());

        return jobOffer;
    }
}
