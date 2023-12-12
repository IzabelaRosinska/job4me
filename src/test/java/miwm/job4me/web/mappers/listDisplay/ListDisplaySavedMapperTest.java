package miwm.job4me.web.mappers.listDisplay;

import miwm.job4me.model.offer.JobOffer;
import miwm.job4me.model.users.Employer;
import miwm.job4me.web.model.listDisplay.ListDisplayDto;
import miwm.job4me.web.model.offer.JobOfferDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListDisplaySavedMapperTest {
    @Mock
    private ListDisplayMapper listDisplayMapper;

    @InjectMocks
    private ListDisplaySavedMapper listDisplaySavedMapper;

    private Employer employer;
    private JobOffer jobOffer;
    private JobOfferDto jobOfferDto;

    private ListDisplayDto jobOfferDisplayDto;

    @BeforeEach
    void setUp() {
        employer = Employer.builder()
                .id(1L)
                .companyName("companyName")
                .displayDescription("displayDescription")
                .photo("photo")
                .build();

        jobOffer = JobOffer.builder()
                .id(1L)
                .offerName("offerName")
                .description("description")
                .employer(employer)
                .salaryFrom(1000)
                .build();

        jobOfferDto = new JobOfferDto();
        jobOfferDto.setId(jobOffer.getId());
        jobOfferDto.setOfferName(jobOffer.getOfferName());
        jobOfferDto.setDescription(jobOffer.getDescription());
        jobOfferDto.setSalaryFrom(jobOffer.getSalaryFrom());
        jobOfferDto.setEmployerId(jobOffer.getEmployer().getId());

        jobOfferDisplayDto = new ListDisplayDto();
        jobOfferDisplayDto.setId(jobOffer.getId());
        jobOfferDisplayDto.setName(jobOffer.getOfferName());
        jobOfferDisplayDto.setDisplayDescription("testContext");
        jobOfferDisplayDto.setPhoto(jobOffer.getEmployer().getPhoto());
    }

    @Test
    @DisplayName("test toDtoFromEmployer - should return dto with merged values from entity")
    void toDtoFromEmployer() {
        ListDisplayDto result = listDisplaySavedMapper.toDtoFromEmployer(employer);

        assertEquals(employer.getId(), result.getId());
        assertEquals(employer.getCompanyName(), result.getName());
        assertEquals(employer.getDisplayDescription(), result.getDisplayDescription());
        assertEquals(employer.getPhoto(), result.getPhoto());
    }

    @Test
    @DisplayName("test toDtoFromJobOffer - should return dto with merged values from entity")
    void toDtoFromJobOffer() {
        when(listDisplayMapper.toDtoFromJobOffer(jobOffer)).thenReturn(jobOfferDisplayDto);

        ListDisplayDto result = listDisplaySavedMapper.toDtoFromJobOffer(jobOffer);

        assertEquals(jobOfferDisplayDto.getId(), result.getId());
        assertEquals(jobOfferDisplayDto.getName(), result.getName());
        assertEquals(jobOfferDisplayDto.getDisplayDescription(), result.getDisplayDescription());
        assertEquals(jobOfferDisplayDto.getPhoto(), result.getPhoto());
    }

}
