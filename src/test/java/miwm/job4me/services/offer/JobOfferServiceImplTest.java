package miwm.job4me.services.offer;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.JobOffer;
import miwm.job4me.model.offer.description.ExtraSkill;
import miwm.job4me.model.offer.description.Requirement;
import miwm.job4me.model.offer.parameters.*;
import miwm.job4me.model.users.Employer;
import miwm.job4me.repositories.offer.JobOfferRepository;
import miwm.job4me.services.offer.description.ExtraSkillService;
import miwm.job4me.services.offer.description.RequirementService;
import miwm.job4me.services.offer.parameters.*;
import miwm.job4me.services.recommendation.RecommendationNotifierService;
import miwm.job4me.services.users.EmployerService;
import miwm.job4me.validators.entity.offer.JobOfferValidator;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.web.mappers.offer.JobOfferMapper;
import miwm.job4me.web.model.offer.JobOfferDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobOfferServiceImplTest {
    @Mock
    private JobOfferRepository jobOfferRepository;
    @Mock
    private JobOfferMapper jobOfferMapper;
    @Mock
    private JobOfferValidator jobOfferValidator;
    @Mock
    private IdValidator idValidator;

    @Mock
    private ContractTypeService contractTypeService;
    @Mock
    private EmploymentFormService employmentFormService;
    @Mock
    private IndustryService industryService;
    @Mock
    private LevelService levelService;
    @Mock
    private LocalizationService localizationService;
    @Mock
    private RequirementService requirementService;
    @Mock
    private ExtraSkillService extraSkillService;
    @Mock
    private EmployerService employerService;
    @Mock
    private RecommendationNotifierService recommendationNotifierService;

    @InjectMocks
    private JobOfferServiceImpl jobOfferService;

    private final int MAX_DESCRIPTION_LENGTH = 1000;
    private final String ENTITY_NAME = "JobOffer";

    private Employer employer;
    private JobOffer jobOffer1;
    private JobOffer jobOffer2;
    private JobOfferDto jobOfferDto1;
    private JobOfferDto jobOfferDto2;

    @BeforeEach
    public void setUp() {
        employer = Employer
                .builder()
                .id(1L)
                .build();

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

        jobOffer1 = JobOffer.builder()
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

        jobOfferDto1 = new JobOfferDto();
        jobOfferDto1.setId(jobOffer1.getId());
        jobOfferDto1.setOfferName(jobOffer1.getOfferName());
        jobOfferDto1.setEmployerId(jobOffer1.getEmployer().getId());
        jobOfferDto1.setIndustries(industriesDto);
        jobOfferDto1.setLocalizations(localizationsDto);
        jobOfferDto1.setEmploymentForms(employmentFormsDto);
        jobOfferDto1.setSalaryFrom(jobOffer1.getSalaryFrom());
        jobOfferDto1.setSalaryTo(jobOffer1.getSalaryTo());
        jobOfferDto1.setContractTypes(contractTypesDto);
        jobOfferDto1.setWorkingTime(jobOffer1.getWorkingTime());
        jobOfferDto1.setLevels(levelsDto);
        jobOfferDto1.setRequirements(requirementsDto);
        jobOfferDto1.setExtraSkills(extraSkillsDto);
        jobOfferDto1.setDuties(jobOffer1.getDuties());
        jobOfferDto1.setDescription(jobOffer1.getDescription());
        jobOfferDto1.setIsActive(jobOffer1.getIsActive());

        jobOffer2 = JobOffer
                .builder()
                .id(2L)
                .description("description2")
                .employer(employer)
                .build();

        jobOfferDto2 = new JobOfferDto();
        jobOfferDto2.setId(jobOffer2.getId());
        jobOfferDto2.setDescription(jobOffer2.getDescription());
        jobOfferDto2.setEmployerId(employer.getId());
    }

    @Test
    @DisplayName("Test existsById - return true when JobOffer object exists")
    public void testExistsByIdExists() {
        when(jobOfferRepository.existsById(jobOffer1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(jobOffer1.getId(), ENTITY_NAME);

        assertTrue(jobOfferService.existsById(jobOffer1.getId()));
    }

    @Test
    @DisplayName("Test existsById - return false when JobOffer object does not exist")
    public void testExistsByIdDoesNotExist() {
        when(jobOfferRepository.existsById(jobOffer1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(jobOffer1.getId(), ENTITY_NAME);

        assertFalse(jobOfferService.existsById(jobOffer1.getId()));
    }

    @Test
    @DisplayName("Test strictExistsById - do nothing when JobOffer object exists")
    public void testStrictExistsByIdExists() {
        when(jobOfferRepository.existsById(jobOffer1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(jobOffer1.getId(), ENTITY_NAME);

        assertDoesNotThrow(() -> jobOfferService.strictExistsById(jobOffer1.getId()));
    }

    @Test
    @DisplayName("Test strictExistsById - throw NoSuchElementFoundException when JobOffer object does not exist")
    public void testStrictExistsByIdDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, jobOffer1.getId());

        when(jobOfferRepository.existsById(jobOffer1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(jobOffer1.getId(), ENTITY_NAME);

        try {
            jobOfferService.strictExistsById(jobOffer1.getId());
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test strictExistsById - throw InvalidArgumentException when id is null")
    public void testStrictExistsByIdNull() {
        String expectedMessage = ExceptionMessages.idCannotBeNull(ENTITY_NAME);

        doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

        try {
            jobOfferService.strictExistsById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findAll - return all existing JobOffer objects")
    public void testFindAll() {
        when(jobOfferRepository.findAll()).thenReturn(List.of(jobOffer1, jobOffer2));
        when(jobOfferMapper.toDto(jobOffer1)).thenReturn(jobOfferDto1);
        when(jobOfferMapper.toDto(jobOffer2)).thenReturn(jobOfferDto2);

        Set<JobOfferDto> result = jobOfferService.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(jobOfferDto1));
        assertTrue(result.contains(jobOfferDto2));
    }

    @Test
    @DisplayName("Test findAll - return empty set when there are no JobOffer objects")
    public void testFindAllEmpty() {
        when(jobOfferRepository.findAll()).thenReturn(List.of());

        Set<JobOfferDto> result = jobOfferService.findAll();

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Test findById - return JobOffer object when it exists")
    public void testFindById() {
        when(jobOfferRepository.findById(jobOffer1.getId())).thenReturn(java.util.Optional.of(jobOffer1));
        when(jobOfferMapper.toDto(jobOffer1)).thenReturn(jobOfferDto1);

        JobOfferDto result = jobOfferService.findById(jobOffer1.getId());

        assertEquals(jobOfferDto1, result);
    }

    @Test
    @DisplayName("Test findById - throw NoSuchElementFoundException when JobOffer object does not exist")
    public void testFindByIdNullId() {
        String expectedMessage = ExceptionMessages.elementNotFound("JobOffer", jobOffer1.getId());
        when(jobOfferRepository.findById(jobOffer1.getId())).thenReturn(java.util.Optional.empty());

        try {
            jobOfferService.findById(jobOffer1.getId());
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findById - throw InvalidArgumentException when id is null")
    public void testFindByIdNull() {
        String expectedMessage = ExceptionMessages.idCannotBeNull(ENTITY_NAME);

        doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

        try {
            jobOfferService.findById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test save - return JobOffer object when it is valid")
    public void testSaveValid() {
        when(employerService.getAuthEmployer()).thenReturn(employer);
        doNothing().when(jobOfferValidator).validate(jobOffer1);
        doNothing().when(idValidator).validateNoIdForCreate(jobOffer1.getId(), ENTITY_NAME);
        when(jobOfferRepository.save(jobOffer1)).thenReturn(jobOffer1);
        when(jobOfferMapper.toDto(jobOffer1)).thenReturn(jobOfferDto1);
        doNothing().when(recommendationNotifierService).notifyUpdatedOffer(jobOffer1.getId());

        JobOfferDto result = jobOfferService.save(jobOffer1);

        assertEquals(jobOfferDto1, result);
        verify(recommendationNotifierService, times(1)).notifyUpdatedOffer(jobOffer1.getId());
    }

    @Test
    @DisplayName("Test save - throw InvalidArgumentException when JobOffer object is null")
    public void testSaveThrowExceptionNull() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        doThrow(new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME))).when(jobOfferValidator).validate(null);

        try {
            jobOfferService.save(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
            verify(recommendationNotifierService, times(0)).notifyUpdatedOffer(jobOffer1.getId());
        }
    }

    @Test
    @DisplayName("Test save - throw InvalidArgumentException when JobOfferValidator fails")
    public void testSaveThrowExceptionJobOfferValidatorFails() {
        String expectedMessage = ExceptionMessages.textTooLong(ENTITY_NAME, "description", MAX_DESCRIPTION_LENGTH);

        jobOffer1.setDescription("a".repeat(MAX_DESCRIPTION_LENGTH + 1));
        doThrow(new InvalidArgumentException(ExceptionMessages.textTooLong(ENTITY_NAME, "description", MAX_DESCRIPTION_LENGTH))).when(jobOfferValidator).validate(jobOffer1);

        try {
            jobOfferService.save(jobOffer1);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
            verify(recommendationNotifierService, times(0)).notifyUpdatedOffer(jobOffer1.getId());
        }
    }

    @Test
    @DisplayName("Test delete - JobOffer object exists")
    public void testDeleteJobOfferExists() {
        doNothing().when(idValidator).validateLongId(jobOffer1.getId(), ENTITY_NAME);
        when(jobOfferRepository.existsById(jobOffer1.getId())).thenReturn(true);
        doNothing().when(jobOfferRepository).delete(jobOffer1);
        doNothing().when(recommendationNotifierService).notifyRemovedOffer(jobOffer1.getId());

        assertDoesNotThrow(() -> jobOfferService.delete(jobOffer1));
        verify(recommendationNotifierService, times(1)).notifyRemovedOffer(jobOffer1.getId());
    }

    @Test
    @DisplayName("Test delete - JobOffer object does not exist")
    public void testDeleteJobOfferDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, jobOffer1.getId());
        when(jobOfferRepository.existsById(jobOffer1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(jobOffer1.getId(), ENTITY_NAME);

        try {
            jobOfferService.delete(jobOffer1);
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
            verify(recommendationNotifierService, times(0)).notifyRemovedOffer(jobOffer1.getId());
        }
    }

    @Test
    @DisplayName("Test delete - JobOffer object is null")
    public void testDeleteJobOfferNull() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        try {
            jobOfferService.delete(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
            verify(recommendationNotifierService, times(0)).notifyRemovedOffer(jobOffer1.getId());
        }
    }

    @Test
    @DisplayName("Test delete - JobOffer id is null")
    public void testDeleteJobOfferIdNull() {
        String expectedMessage = ExceptionMessages.idCannotBeNull(ENTITY_NAME);

        doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);
        jobOffer1.setId(null);

        try {
            jobOfferService.delete(jobOffer1);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
            verify(recommendationNotifierService, times(0)).notifyRemovedOffer(jobOffer1.getId());
        }
    }

    @Test
    @DisplayName("Test delete by id - JobOffer object exists")
    public void testDeleteByIdJobOfferExists() {
        when(jobOfferRepository.existsById(jobOffer1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(jobOffer1.getId(), ENTITY_NAME);
        doNothing().when(jobOfferRepository).deleteById(jobOffer1.getId());
        doNothing().when(recommendationNotifierService).notifyRemovedOffer(jobOffer1.getId());

        assertDoesNotThrow(() -> jobOfferService.deleteById(jobOffer1.getId()));
        verify(recommendationNotifierService, times(1)).notifyRemovedOffer(jobOffer1.getId());
    }

    @Test
    @DisplayName("Test delete by id - JobOffer object does not exist")
    public void testDeleteByIdDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, jobOffer1.getId());

        when(jobOfferRepository.existsById(jobOffer1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(jobOffer1.getId(), ENTITY_NAME);

        try {
            jobOfferService.deleteById(jobOffer1.getId());
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
            verify(recommendationNotifierService, times(0)).notifyRemovedOffer(jobOffer1.getId());
        }
    }

    @Test
    @DisplayName("Test delete by id - id is null")
    public void testDeleteByIdNull() {
        String expectedMessage = ExceptionMessages.idCannotBeNull(ENTITY_NAME);

        doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

        try {
            jobOfferService.deleteById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
            verify(recommendationNotifierService, times(0)).notifyRemovedOffer(jobOffer1.getId());
        }
    }

    @Test
    @DisplayName("Test update - update and return JobOfferDto object when it is valid")
    public void testUpdateJobOfferExists() {
        doNothing().when(idValidator).validateLongId(jobOffer1.getId(), ENTITY_NAME);
        when(jobOfferRepository.existsById(jobOffer1.getId())).thenReturn(true);
        doNothing().when(jobOfferValidator).validateDto(jobOfferDto1);
        when(jobOfferMapper.toEntity(jobOfferDto1)).thenReturn(jobOffer1);
        when(jobOfferRepository.save(jobOffer1)).thenReturn(jobOffer1);
        when(jobOfferMapper.toDto(jobOffer1)).thenReturn(jobOfferDto1);
        doNothing().when(requirementService).deleteAllByJobOfferId(jobOffer1.getId());
        doNothing().when(extraSkillService).deleteAllByJobOfferId(jobOffer1.getId());

        JobOfferDto result = jobOfferService.update(jobOfferDto1.getId(), jobOfferDto1);

        assertEquals(jobOfferDto1, result);
        verify(recommendationNotifierService, times(1)).notifyUpdatedOffer(jobOffer1.getId());
    }

    @Test
    @DisplayName("Test update - throw NoSuchElementFoundException when IdValidator fails")
    public void testUpdateJobOfferDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, jobOffer1.getId());
        doThrow(new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, jobOffer1.getId()))).when(idValidator).validateLongId(jobOffer1.getId(), ENTITY_NAME);

        try {
            jobOfferService.update(jobOfferDto1.getId(), jobOfferDto1);
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
            verify(recommendationNotifierService, times(0)).notifyUpdatedOffer(jobOffer1.getId());
        }
    }

    @Test
    @DisplayName("Test update - throw InvalidArgumentException when JobOfferDto object is null and JobOfferValidator fails")
    public void testUpdateJobOfferDtoNull() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        doNothing().when(idValidator).validateLongId(jobOffer1.getId(), ENTITY_NAME);
        when(jobOfferRepository.existsById(jobOffer1.getId())).thenReturn(true);
        doThrow(new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME))).when(jobOfferValidator).validateDto(null);

        try {
            jobOfferService.update(1L, null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
            verify(recommendationNotifierService, times(0)).notifyUpdatedOffer(jobOffer1.getId());
        }
    }


    @Test
    @DisplayName("Test activateOffer - activate JobOffer object when it exists and is already active")
    public void testActivateOfferExistsActive() {
        doNothing().when(idValidator).validateLongId(jobOffer1.getId(), ENTITY_NAME);
        when(jobOfferRepository.findById(jobOffer1.getId())).thenReturn(java.util.Optional.of(jobOffer1));
        when(jobOfferMapper.toDto(jobOffer1)).thenReturn(jobOfferDto1);
        ;

        assertDoesNotThrow(() -> jobOfferService.activateOffer(jobOffer1.getId()));
        verify(recommendationNotifierService, times(0)).notifyUpdatedOffer(jobOffer1.getId());
    }

    @Test
    @DisplayName("Test activateOffer - activate JobOffer object when it exists and is not active")
    public void testActivateOfferExistsNotActive() {
        jobOffer1.setIsActive(false);

        doNothing().when(idValidator).validateLongId(jobOffer1.getId(), ENTITY_NAME);
        when(jobOfferRepository.findById(jobOffer1.getId())).thenReturn(Optional.of(jobOffer1));
        when(jobOfferRepository.save(jobOffer1)).thenReturn(jobOffer1);
        when(jobOfferMapper.toDto(jobOffer1)).thenReturn(jobOfferDto1);
        doNothing().when(recommendationNotifierService).notifyUpdatedOffer(jobOffer1.getId());

        assertDoesNotThrow(() -> jobOfferService.activateOffer(jobOffer1.getId()));
        verify(recommendationNotifierService, times(1)).notifyUpdatedOffer(jobOffer1.getId());
    }

    @Test
    @DisplayName("Test activateOffer - throw NoSuchElementFoundException when JobOffer object does not exist")
    public void testActivateOfferDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, jobOffer1.getId());

        when(jobOfferRepository.findById(jobOffer1.getId())).thenReturn(Optional.empty());
        doNothing().when(idValidator).validateLongId(jobOffer1.getId(), ENTITY_NAME);

        try {
            jobOfferService.activateOffer(jobOffer1.getId());
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
            verify(recommendationNotifierService, times(0)).notifyUpdatedOffer(jobOffer1.getId());
        }
    }

    @Test
    @DisplayName("Test activateOffer - fail when IdValidator fails (id is null)")
    public void testActivateOfferNull() {
        String expectedMessage = ExceptionMessages.idCannotBeNull(ENTITY_NAME);

        doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

        try {
            jobOfferService.activateOffer(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
            verify(recommendationNotifierService, times(0)).notifyUpdatedOffer(jobOffer1.getId());
        }
    }

    @Test
    @DisplayName("Test deactivateOffer - deactivate JobOffer object when it exists and is already inactive")
    public void testDeactivateOfferExistsNotActive() {
        jobOffer1.setIsActive(false);

        doNothing().when(idValidator).validateLongId(jobOffer1.getId(), ENTITY_NAME);
        when(jobOfferRepository.findById(jobOffer1.getId())).thenReturn(Optional.of(jobOffer1));
        when(jobOfferMapper.toDto(jobOffer1)).thenReturn(jobOfferDto1);

        assertDoesNotThrow(() -> jobOfferService.deactivateOffer(jobOffer1.getId()));
        verify(recommendationNotifierService, times(0)).notifyRemovedOffer(jobOffer1.getId());
    }

    @Test
    @DisplayName("Test deactivateOffer - deactivate JobOffer object when it exists and is active")
    public void testDeactivateOfferExistsActive() {
        doNothing().when(idValidator).validateLongId(jobOffer1.getId(), ENTITY_NAME);
        when(jobOfferRepository.findById(jobOffer1.getId())).thenReturn(Optional.of(jobOffer1));
        when(jobOfferRepository.save(jobOffer1)).thenReturn(jobOffer1);
        when(jobOfferMapper.toDto(jobOffer1)).thenReturn(jobOfferDto1);
        doNothing().when(recommendationNotifierService).notifyRemovedOffer(jobOffer1.getId());

        assertDoesNotThrow(() -> jobOfferService.deactivateOffer(jobOffer1.getId()));
        verify(recommendationNotifierService, times(1)).notifyRemovedOffer(jobOffer1.getId());
    }

    @Test
    @DisplayName("Test deactivateOffer - throw NoSuchElementFoundException when JobOffer object does not exist")
    public void testDeactivateOfferDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, jobOffer1.getId());

        when(jobOfferRepository.findById(jobOffer1.getId())).thenReturn(Optional.empty());
        doNothing().when(idValidator).validateLongId(jobOffer1.getId(), ENTITY_NAME);

        try {
            jobOfferService.deactivateOffer(jobOffer1.getId());
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
            verify(recommendationNotifierService, times(0)).notifyRemovedOffer(jobOffer1.getId());
        }
    }

    @Test
    @DisplayName("Test deactivateOffer - fail when IdValidator fails (id is null)")
    public void testDeactivateOfferNull() {
        String expectedMessage = ExceptionMessages.idCannotBeNull(ENTITY_NAME);

        doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

        try {
            jobOfferService.deactivateOffer(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
            verify(recommendationNotifierService, times(0)).notifyRemovedOffer(jobOffer1.getId());
        }
    }

    @Test
    @DisplayName("Test canEmployerHaveAccessToJobOffer - return true when employer has access to JobOffer")
    public void testCanEmployerHaveAccessToJobOfferTrue() {
        when(employerService.getAuthEmployer()).thenReturn(employer);
        when(jobOfferRepository.findById(jobOffer1.getId())).thenReturn(Optional.of(jobOffer1));
        when(jobOfferRepository.findById(jobOffer2.getId())).thenReturn(Optional.of(jobOffer2));

        boolean result1 = jobOfferService.canEmployerHaveAccessToJobOffer(jobOffer1.getId());
        boolean result2 = jobOfferService.canEmployerHaveAccessToJobOffer(jobOffer2.getId());

        assertTrue(result1);
        assertTrue(result2);
    }

    @Test
    @DisplayName("Test canEmployerHaveAccessToJobOffer - return false when employer does not have access to JobOffer")
    public void testCanEmployerHaveAccessToJobOfferFalse() {
        Employer employer2 = Employer.builder().id(2L).build();
        when(employerService.getAuthEmployer()).thenReturn(employer2);
        when(jobOfferRepository.findById(jobOffer1.getId())).thenReturn(Optional.of(jobOffer1));
        when(jobOfferRepository.findById(jobOffer2.getId())).thenReturn(Optional.of(jobOffer2));

        boolean result1 = jobOfferService.canEmployerHaveAccessToJobOffer(jobOffer1.getId());
        boolean result2 = jobOfferService.canEmployerHaveAccessToJobOffer(jobOffer2.getId());

        assertFalse(result1);
        assertFalse(result2);
    }

}
