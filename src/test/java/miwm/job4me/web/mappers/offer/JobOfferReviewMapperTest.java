package miwm.job4me.web.mappers.offer;

import miwm.job4me.model.offer.JobOffer;
import miwm.job4me.model.users.Employer;
import miwm.job4me.web.model.offer.JobOfferReviewDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JobOfferReviewMapperTest {

    @Mock
    private JobOfferMapper jobOfferMapper;

    @InjectMocks
    private JobOfferReviewMapper jobOfferReviewMapper;

    private JobOffer jobOffer;
    private JobOfferReviewDto jobOfferReviewDto;

    @BeforeEach
    void setUp() {
        jobOffer = new JobOffer();
        jobOffer.setId(1L);
        jobOffer.setOfferName("Java Developer");
        jobOffer.setEmployer(Employer.builder().id(2L).build());
        jobOffer.setIndustries(new HashSet<>());
        jobOffer.setLocalizations(new HashSet<>());
        jobOffer.setEmploymentForms(new HashSet<>());
        jobOffer.setSalaryFrom(5000);
        jobOffer.setSalaryTo(10000);
        jobOffer.setContractTypes(new HashSet<>());
        jobOffer.setWorkingTime("Full-time");
        jobOffer.setLevels(new HashSet<>());
        jobOffer.setRequirements(new HashSet<>());
        jobOffer.setExtraSkills(new HashSet<>());
        jobOffer.setDuties("Develop Java applications");
        jobOffer.setDescription("Java Developer needed for a large tech company");

        jobOfferReviewDto = new JobOfferReviewDto();
        jobOfferReviewDto.setId(1L);
        jobOfferReviewDto.setOfferName("Java Developer");
        jobOfferReviewDto.setEmployerId(2L);
        jobOfferReviewDto.setIndustries(new ArrayList<>());
        jobOfferReviewDto.setLocalizations(new ArrayList<>());
        jobOfferReviewDto.setEmploymentForms(new ArrayList<>());
        jobOfferReviewDto.setSalaryFrom(5000);
        jobOfferReviewDto.setSalaryTo(10000);
        jobOfferReviewDto.setContractTypes(new ArrayList<>());
        jobOfferReviewDto.setWorkingTime("Full-time");
        jobOfferReviewDto.setLevels(new ArrayList<>());
        jobOfferReviewDto.setRequirements(new ArrayList<>());
        jobOfferReviewDto.setExtraSkills(new ArrayList<>());
        jobOfferReviewDto.setDuties("Develop Java applications");
        jobOfferReviewDto.setDescription("Java Developer needed for a large tech company");
        jobOfferReviewDto.setIsSaved(true);
    }

    @Test
    void testJobOfferToJobOfferDtoWhenAllFieldsAreValidThenReturnJobOfferReviewDto() {
        when(jobOfferMapper.industriesSetToStringList(jobOffer.getIndustries())).thenReturn(jobOfferReviewDto.getIndustries());
        when(jobOfferMapper.localizationsSetToStringList(jobOffer.getLocalizations())).thenReturn(jobOfferReviewDto.getLocalizations());
        when(jobOfferMapper.employmentFormsSetToStringList(jobOffer.getEmploymentForms())).thenReturn(jobOfferReviewDto.getEmploymentForms());
        when(jobOfferMapper.contractTypesSetToStringList(jobOffer.getContractTypes())).thenReturn(jobOfferReviewDto.getContractTypes());
        when(jobOfferMapper.levelsSetToStringList(jobOffer.getLevels())).thenReturn(jobOfferReviewDto.getLevels());
        when(jobOfferMapper.requirementsSetToStringList(jobOffer.getRequirements())).thenReturn(jobOfferReviewDto.getRequirements());
        when(jobOfferMapper.extraSkillsSetToStringList(jobOffer.getExtraSkills())).thenReturn(jobOfferReviewDto.getExtraSkills());

        JobOfferReviewDto resultDto = jobOfferReviewMapper.jobOfferToJobOfferDto(jobOffer, true);

        assertEquals(jobOfferReviewDto.getId(), resultDto.getId());
        assertEquals(jobOfferReviewDto.getOfferName(), resultDto.getOfferName());
        assertEquals(jobOfferReviewDto.getEmployerId(), resultDto.getEmployerId());
        assertEquals(jobOfferReviewDto.getIndustries(), resultDto.getIndustries());
        assertEquals(jobOfferReviewDto.getLocalizations(), resultDto.getLocalizations());
        assertEquals(jobOfferReviewDto.getEmploymentForms(), resultDto.getEmploymentForms());
        assertEquals(jobOfferReviewDto.getSalaryFrom(), resultDto.getSalaryFrom());
        assertEquals(jobOfferReviewDto.getSalaryTo(), resultDto.getSalaryTo());
        assertEquals(jobOfferReviewDto.getContractTypes(), resultDto.getContractTypes());
        assertEquals(jobOfferReviewDto.getWorkingTime(), resultDto.getWorkingTime());
        assertEquals(jobOfferReviewDto.getLevels(), resultDto.getLevels());
        assertEquals(jobOfferReviewDto.getRequirements(), resultDto.getRequirements());
        assertEquals(jobOfferReviewDto.getExtraSkills(), resultDto.getExtraSkills());
        assertEquals(jobOfferReviewDto.getDuties(), resultDto.getDuties());
        assertEquals(jobOfferReviewDto.getDescription(), resultDto.getDescription());
        assertEquals(jobOfferReviewDto.getIsSaved(), resultDto.getIsSaved());
    }
}
