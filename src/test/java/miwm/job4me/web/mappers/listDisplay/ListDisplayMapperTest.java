package miwm.job4me.web.mappers.listDisplay;

import miwm.job4me.model.event.JobFair;
import miwm.job4me.model.event.JobFairEmployerParticipation;
import miwm.job4me.model.offer.JobOffer;
import miwm.job4me.model.offer.parameters.ContractType;
import miwm.job4me.model.offer.parameters.EmploymentForm;
import miwm.job4me.model.offer.parameters.Level;
import miwm.job4me.model.offer.parameters.Localization;
import miwm.job4me.model.users.Employee;
import miwm.job4me.model.users.Employer;
import miwm.job4me.web.mappers.offer.JobOfferMapper;
import miwm.job4me.web.model.listDisplay.ListDisplayDto;
import miwm.job4me.web.model.offer.JobOfferDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListDisplayMapperTest {
    @Mock
    private JobOfferMapper jobOfferMapper;
    @InjectMocks
    private ListDisplayMapper listDisplayMapper;

    private JobFair jobFair;
    private Employer employer;
    private JobFairEmployerParticipation jobFairEmployerParticipation;
    private Employee employee;
    private JobOffer jobOffer;
    private JobOfferDto jobOfferDto;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime later = now.plusDays(1);

        employer = Employer.builder()
                .id(1L)
                .companyName("companyName")
                .displayDescription("displayDescription")
                .photo("photo")
                .build();

        jobFair = JobFair.builder()
                .id(1L)
                .name("name")
                .address("address")
                .dateStart(now)
                .dateEnd(later)
                .displayDescription("displayDescription")
                .photo("photo")
                .build();

        jobFairEmployerParticipation = JobFairEmployerParticipation.builder()
                .id(1L)
                .jobFair(jobFair)
                .employer(employer)
                .isAccepted(true)
                .build();

        employee = Employee.builder()
                .id(1L)
                .firstName("firstName")
                .lastName("lastName")
                .contactEmail("contactEmail")
                .telephone("telephone")
                .build();

        HashSet<Localization> localizations = new HashSet<>();
        localizations.add(Localization.builder().city("city 1").build());

        HashSet<EmploymentForm> employmentForms = new HashSet<>();
        employmentForms.add(EmploymentForm.builder().name("employment form 1").build());
        employmentForms.add(EmploymentForm.builder().name("employment form 2").build());

        HashSet<ContractType> contractTypes = new HashSet<>();
        contractTypes.add(ContractType.builder().name("contract type 1").build());
        contractTypes.add(ContractType.builder().name("contract type 2").build());
        contractTypes.add(ContractType.builder().name("contract type 3").build());

        HashSet<Level> levels = new HashSet<>();
        levels.add(Level.builder().name("level 1").build());

        ArrayList<String> localizationsDto = new ArrayList<>();
        localizationsDto.add("city 1");

        ArrayList<String> employmentFormsDto = new ArrayList<>();
        employmentFormsDto.add("employment form 1");
        employmentFormsDto.add("employment form 2");

        ArrayList<String> contractTypesDto = new ArrayList<>();
        contractTypesDto.add("contract type 1");
        contractTypesDto.add("contract type 2");
        contractTypesDto.add("contract type 3");

        ArrayList<String> levelsDto = new ArrayList<>();
        levelsDto.add("level 1");

        jobOffer = JobOffer.builder()
                .id(1L)
                .offerName("offerName")
                .description("description")
                .employer(employer)
                .salaryFrom(1000)
                .localizations(localizations)
                .employmentForms(employmentForms)
                .contractTypes(contractTypes)
                .levels(levels)
                .build();

        jobOfferDto = new JobOfferDto();
        jobOfferDto.setId(jobOffer.getId());
        jobOfferDto.setOfferName(jobOffer.getOfferName());
        jobOfferDto.setDescription(jobOffer.getDescription());
        jobOfferDto.setSalaryFrom(jobOffer.getSalaryFrom());
        jobOfferDto.setEmployerId(jobOffer.getEmployer().getId());
        jobOfferDto.setLocalizations(localizationsDto);
        jobOfferDto.setEmploymentForms(employmentFormsDto);
        jobOfferDto.setContractTypes(contractTypesDto);
        jobOfferDto.setLevels(levelsDto);
    }


    @Test
    @DisplayName("test toDtoFromJobFair - should return dto with merged values from entity")
    void toDtoFromJobFair() {
        String expectedDescription = jobFair.getDateStart() + " - " + jobFair.getDateEnd() + "\n" + jobFair.getAddress() + "\n" + jobFair.getDisplayDescription();

        ListDisplayDto listDisplayDto = listDisplayMapper.toDtoFromJobFair(jobFair);

        assertEquals(jobFair.getId(), listDisplayDto.getId());
        assertEquals(jobFair.getName(), listDisplayDto.getName());
        assertEquals(listDisplayDto.getDisplayDescription(), expectedDescription);
        assertEquals(jobFair.getPhoto(), listDisplayDto.getPhoto());
    }

    @Test
    @DisplayName("test toDtoFromJobFairEmployerParticipation - should return dto with merged values from entity")
    void toDtoFromJobFairEmployerParticipation() {
        String expectedDescription = "Zgłoszenie firmy #" + employer.getId() + employer.getCompanyName() + " do targów pracy #" + jobFair.getId() + jobFair.getName();
        String expectedName = jobFair.getName() + " - " + employer.getCompanyName();

        ListDisplayDto listDisplayDto = listDisplayMapper.toDtoFromJobFairEmployerParticipation(jobFairEmployerParticipation);

        assertEquals(jobFairEmployerParticipation.getId(), listDisplayDto.getId());
        assertEquals(expectedName, listDisplayDto.getName());
        assertEquals(expectedDescription, listDisplayDto.getDisplayDescription());
        assertEquals(employer.getPhoto(), listDisplayDto.getPhoto());
    }

    @Test
    @DisplayName("test toDtoFromEmployee with empty telephone - should return dto with merged values from entity")
    void toDtoFromEmployeeEmptyTelephone() {
        employee.setTelephone("");
        String expectedDescription = employee.getContactEmail();
        String expectedName = employee.getFirstName() + " " + employee.getLastName();

        ListDisplayDto listDisplayDto = listDisplayMapper.toDtoFromEmployee(employee);

        assertEquals(employee.getId(), listDisplayDto.getId());
        assertEquals(expectedName, listDisplayDto.getName());
        assertEquals(expectedDescription, listDisplayDto.getDisplayDescription());
        assertEquals("", listDisplayDto.getPhoto());
    }

    @Test
    @DisplayName("test toDtoFromEmployee present telephone - should return dto with merged values from entity")
    void toDtoFromEmployeeNonEmptyTelephone() {
        String expectedDescription = employee.getContactEmail() + "\n" + employee.getTelephone();
        String expectedName = employee.getFirstName() + " " + employee.getLastName();

        ListDisplayDto listDisplayDto = listDisplayMapper.toDtoFromEmployee(employee);

        assertEquals(employee.getId(), listDisplayDto.getId());
        assertEquals(expectedName, listDisplayDto.getName());
        assertEquals(expectedDescription, listDisplayDto.getDisplayDescription());
        assertEquals("", listDisplayDto.getPhoto());
    }

    @Test
    @DisplayName("test toDtoFromEmployer - should return dto with merged values from entity")
    void toDtoFromEmployer() {
        ListDisplayDto listDisplayDto = listDisplayMapper.toDtoFromEmployer(employer);

        assertEquals(employer.getId(), listDisplayDto.getId());
        assertEquals(employer.getCompanyName(), listDisplayDto.getName());
        assertEquals(employer.getDisplayDescription(), listDisplayDto.getDisplayDescription());
        assertEquals(employer.getPhoto(), listDisplayDto.getPhoto());
    }

    @Test
    @DisplayName("test toDtoFromJobOffer null salaryTo - should return dto with merged values from entity")
    void toDtoFromJobOfferNullSalaryTo() {
        String expectedDescription = "companyName\n1000 zł\ncity 1\nemployment form 1, employment form 2\ncontract type 1, contract type 2, contract type 3\nlevel 1\n";
        String expectedName = jobOffer.getOfferName();

        when(jobOfferMapper.toDto(jobOffer)).thenReturn(jobOfferDto);

        // when
        ListDisplayDto listDisplayDto = listDisplayMapper.toDtoFromJobOffer(jobOffer);

        // then
        assertEquals(jobOffer.getId(), listDisplayDto.getId());
        assertEquals(expectedName, listDisplayDto.getName());
        assertEquals(expectedDescription, listDisplayDto.getDisplayDescription());
        assertEquals(jobOffer.getEmployer().getPhoto(), listDisplayDto.getPhoto());
    }

    @Test
    @DisplayName("test toDtoFromJobOffer present salaryTo - should return dto with merged values from entity")
    void toDtoFromJobOfferPresentSalaryTo() {
        jobOffer.setSalaryTo(2000);
        String expectedDescription = "companyName\n1000 - 2000 zł\ncity 1\nemployment form 1, employment form 2\ncontract type 1, contract type 2, contract type 3\nlevel 1\n";
        String expectedName = jobOffer.getOfferName();

        when(jobOfferMapper.toDto(jobOffer)).thenReturn(jobOfferDto);

        // when
        ListDisplayDto listDisplayDto = listDisplayMapper.toDtoFromJobOffer(jobOffer);

        // then
        assertEquals(jobOffer.getId(), listDisplayDto.getId());
        assertEquals(expectedName, listDisplayDto.getName());
        assertEquals(expectedDescription, listDisplayDto.getDisplayDescription());
        assertEquals(jobOffer.getEmployer().getPhoto(), listDisplayDto.getPhoto());
    }

}
