package miwm.job4me.web.mappers.offer;

import miwm.job4me.model.offer.JobOffer;
import miwm.job4me.model.offer.description.ExtraSkill;
import miwm.job4me.model.offer.description.Requirement;
import miwm.job4me.model.offer.parameters.*;
import miwm.job4me.model.users.Employer;
import miwm.job4me.web.model.offer.JobOfferDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class JobOfferMapperTest {
    @InjectMocks
    private JobOfferMapper jobOfferMapper;

    private JobOffer jobOffer;
    private JobOfferDto jobOfferDto;

    @BeforeEach
    void setUp() {
        Employer employer = Employer.builder().id(1L).build();

        HashSet<Industry> industries = new HashSet<>();
        industries.add(Industry.builder().name("industry 1").build());

        HashSet<Localization> localizations = new HashSet<>();
        localizations.add(Localization.builder().city("city 1").build());

        HashSet<EmploymentForm> employmentForms = new HashSet<>();
        employmentForms.add(EmploymentForm.builder().id(1L).name("employment form 1").build());
        employmentForms.add(EmploymentForm.builder().id(2L).name("employment form 2").build());

        HashSet<ContractType> contractTypes = new HashSet<>();
        contractTypes.add(ContractType.builder().name("contract type 1").build());

        HashSet<Level> levels = new HashSet<>();
        levels.add(Level.builder().name("level 1").build());

        HashSet<Requirement> requirements = new HashSet<>();
        requirements.add(Requirement.builder().description("requirement 1").build());

        HashSet<ExtraSkill> extraSkills = new HashSet<>();
        extraSkills.add(ExtraSkill.builder().description("extra skill 1").build());

        ArrayList<String> industriesDto = new ArrayList<>();
        industriesDto.add("industry 1");

        ArrayList<String> localizationsDto = new ArrayList<>();
        localizationsDto.add("city 1");

        ArrayList<String> employmentFormsDto = new ArrayList<>();
        employmentFormsDto.add("employment form 1");
        employmentFormsDto.add("employment form 2");

        ArrayList<String> contractTypesDto = new ArrayList<>();
        contractTypesDto.add("contract type 1");

        ArrayList<String> levelsDto = new ArrayList<>();
        levelsDto.add("level 1");

        ArrayList<String> requirementsDto = new ArrayList<>();
        requirementsDto.add("requirement 1");

        ArrayList<String> extraSkillsDto = new ArrayList<>();
        extraSkillsDto.add("extra skill 1");

        jobOffer = JobOffer.builder()
                .id(1L)
                .offerName("offerName")
                .employer(employer)
                .industries(industries)
                .localizations(localizations)
                .employmentForms(employmentForms)
                .salaryFrom(1000)
                .salaryTo(2000)
                .contractTypes(contractTypes)
                .workingTime("working time")
                .levels(levels)
                .requirements(requirements)
                .extraSkills(extraSkills)
                .duties("duties")
                .description("description")
                .isActive(true)
                .build();

        jobOfferDto = new JobOfferDto();
        jobOfferDto.setId(jobOffer.getId());
        jobOfferDto.setOfferName(jobOffer.getOfferName());
        jobOfferDto.setEmployerId(jobOffer.getEmployer().getId());
        jobOfferDto.setIndustries(industriesDto);
        jobOfferDto.setLocalizations(localizationsDto);
        jobOfferDto.setEmploymentForms(employmentFormsDto);
        jobOfferDto.setSalaryFrom(jobOffer.getSalaryFrom());
        jobOfferDto.setSalaryTo(jobOffer.getSalaryTo());
        jobOfferDto.setContractTypes(contractTypesDto);
        jobOfferDto.setWorkingTime(jobOffer.getWorkingTime());
        jobOfferDto.setLevels(levelsDto);
        jobOfferDto.setRequirements(requirementsDto);
        jobOfferDto.setExtraSkills(extraSkillsDto);
        jobOfferDto.setDuties(jobOffer.getDuties());
        jobOfferDto.setDescription(jobOffer.getDescription());
        jobOfferDto.setIsActive(jobOffer.getIsActive());
    }

    @Test
    @DisplayName("test toDto - should return dto with merged values from entity")
    void toDto() {
        JobOfferDto result = jobOfferMapper.toDto(jobOffer);

        assertEquals(jobOffer.getId(), result.getId());
        assertEquals(jobOffer.getOfferName(), result.getOfferName());
        assertEquals(jobOffer.getEmployer().getId(), result.getEmployerId());
        assertEquals(jobOffer.getIndustries().size(), result.getIndustries().size());
        assertEquals(jobOffer.getLocalizations().size(), result.getLocalizations().size());
        assertEquals(jobOffer.getEmploymentForms().size(), result.getEmploymentForms().size());
        assertEquals(jobOffer.getSalaryFrom(), result.getSalaryFrom());
        assertEquals(jobOffer.getSalaryTo(), result.getSalaryTo());
        assertEquals(jobOffer.getContractTypes().size(), result.getContractTypes().size());
        assertEquals(jobOffer.getWorkingTime(), result.getWorkingTime());
        assertEquals(jobOffer.getLevels().size(), result.getLevels().size());
        assertEquals(jobOffer.getRequirements().size(), result.getRequirements().size());
        assertEquals(jobOffer.getExtraSkills().size(), result.getExtraSkills().size());
        assertEquals(jobOffer.getDuties(), result.getDuties());
        assertEquals(jobOffer.getDescription(), result.getDescription());
        assertEquals(jobOffer.getIsActive(), result.getIsActive());
    }

    @Test
    @DisplayName("test toEntity - should return entity with merged values from dto")
    void toEntity() {
        JobOffer result = jobOfferMapper.toEntity(jobOfferDto);

        assertEquals(jobOfferDto.getId(), result.getId());
        assertEquals(jobOfferDto.getOfferName(), result.getOfferName());
        assertEquals(jobOfferDto.getEmployerId(), result.getEmployer().getId());
        assertEquals(jobOfferDto.getIndustries().size(), result.getIndustries().size());
        assertEquals(jobOfferDto.getLocalizations().size(), result.getLocalizations().size());
        assertEquals(jobOfferDto.getEmploymentForms().size(), result.getEmploymentForms().size());
        assertEquals(jobOfferDto.getSalaryFrom(), result.getSalaryFrom());
        assertEquals(jobOfferDto.getSalaryTo(), result.getSalaryTo());
        assertEquals(jobOfferDto.getContractTypes().size(), result.getContractTypes().size());
        assertEquals(jobOfferDto.getWorkingTime(), result.getWorkingTime());
        assertEquals(jobOfferDto.getLevels().size(), result.getLevels().size());
        assertEquals(jobOfferDto.getRequirements().size(), result.getRequirements().size());
        assertEquals(jobOfferDto.getExtraSkills().size(), result.getExtraSkills().size());
        assertEquals(jobOfferDto.getDuties(), result.getDuties());
        assertEquals(jobOfferDto.getDescription(), result.getDescription());
        assertEquals(jobOfferDto.getIsActive(), result.getIsActive());
    }

}
