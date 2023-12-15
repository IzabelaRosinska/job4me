package miwm.job4me.services.event;

import miwm.job4me.emails.EMailService;
import miwm.job4me.exceptions.AuthException;
import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.event.JobFair;
import miwm.job4me.model.event.JobFairEmployerParticipation;
import miwm.job4me.model.offer.JobOffer;
import miwm.job4me.model.users.Employer;
import miwm.job4me.model.users.Organizer;
import miwm.job4me.repositories.event.JobFairEmployerParticipationRepository;
import miwm.job4me.services.users.EmployerService;
import miwm.job4me.services.users.OrganizerService;
import miwm.job4me.validators.entity.event.JobFairEmployerParticipationValidator;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.validators.pagination.PaginationValidator;
import miwm.job4me.web.mappers.event.JobFairEmployerParticipationMapper;
import miwm.job4me.web.mappers.listDisplay.ListDisplayMapper;
import miwm.job4me.web.model.event.JobFairEmployerParticipationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobFairEmployerParticipationServiceImplTest {
    @Mock
    private JobFairEmployerParticipationRepository jobFairEmployerParticipationRepository;
    @Mock
    private JobFairEmployerParticipationMapper jobFairEmployerParticipationMapper;
    @Mock
    private JobFairEmployerParticipationValidator jobFairEmployerParticipationValidator;
    @Mock
    private ListDisplayMapper listDisplayMapper;
    @Mock
    private JobFairService jobFairService;
    @Mock
    private EmployerService employerService;
    @Mock
    private OrganizerService organizerService;
    @Mock
    private IdValidator idValidator;
    @Mock
    private PaginationValidator paginationValidator;
    @Mock
    private EMailService eMailService;
    @InjectMocks
    private JobFairEmployerParticipationServiceImpl jobFairEmployerParticipationService;

    private final String ENTITY_NAME = "JobFairEmployerParticipation";

    private JobOffer jobOffer;
    private JobFairEmployerParticipation jobFairEmployerParticipation1;
    private JobFairEmployerParticipation jobFairEmployerParticipation2;
    private JobFairEmployerParticipationDto jobFairEmployerParticipationDto1;
    private JobFairEmployerParticipationDto jobFairEmployerParticipationDto2;
    private final String employerContactEmail = "employerContactEmail";
    private final String organizerContactEmail = "organizerContactEmail";

    @BeforeEach
    public void setUp() {
        Organizer organizer = Organizer
                .builder()
                .id(1L)
                .build();

        organizer.setContactEmail(organizerContactEmail);

        JobFair jobFair1 = JobFair
                .builder()
                .id(1L)
                .name("name")
                .organizer(organizer)
                .build();

        JobFair jobFair2 = JobFair
                .builder()
                .id(2L)
                .name("name2")
                .organizer(organizer)
                .build();

        Employer employer1 = Employer
                .builder()
                .id(1L)
                .companyName("companyName")
                .contactEmail(employerContactEmail)
                .build();

        Employer employer2 = Employer
                .builder()
                .id(2L)
                .companyName("companyName2")
                .contactEmail(employerContactEmail)
                .build();

        jobFairEmployerParticipation1 = JobFairEmployerParticipation
                .builder()
                .id(1L)
                .jobFair(jobFair1)
                .employer(employer1)
                .build();

        jobFairEmployerParticipationDto1 = new JobFairEmployerParticipationDto();
        jobFairEmployerParticipationDto1.setId(jobFairEmployerParticipation1.getId());
        jobFairEmployerParticipationDto1.setJobFairId(jobFairEmployerParticipation1.getJobFair().getId());
        jobFairEmployerParticipationDto1.setJobFairName(jobFairEmployerParticipation1.getJobFair().getName());
        jobFairEmployerParticipationDto1.setEmployerId(jobFairEmployerParticipation1.getEmployer().getId());
        jobFairEmployerParticipationDto1.setEmployerCompanyName(jobFairEmployerParticipation1.getEmployer().getCompanyName());

        jobFairEmployerParticipation2 = JobFairEmployerParticipation
                .builder()
                .id(2L)
                .jobFair(jobFair2)
                .employer(employer2)
                .build();

        jobFairEmployerParticipationDto2 = new JobFairEmployerParticipationDto();
        jobFairEmployerParticipationDto2.setId(jobFairEmployerParticipation2.getId());
        jobFairEmployerParticipationDto2.setJobFairId(jobFairEmployerParticipation2.getJobFair().getId());
        jobFairEmployerParticipationDto2.setJobFairName(jobFairEmployerParticipation2.getJobFair().getName());
        jobFairEmployerParticipationDto2.setEmployerId(jobFairEmployerParticipation2.getEmployer().getId());
        jobFairEmployerParticipationDto2.setEmployerCompanyName(jobFairEmployerParticipation2.getEmployer().getCompanyName());
    }

    @Test
    @DisplayName("Test existsById - return true when jobFairEmployerParticipation object exists")
    public void testExistsByIdExists() {
        when(jobFairEmployerParticipationRepository.existsById(jobFairEmployerParticipation1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(jobFairEmployerParticipation1.getId(), ENTITY_NAME);

        assertTrue(jobFairEmployerParticipationService.existsById(jobFairEmployerParticipation1.getId()));
    }

    @Test
    @DisplayName("Test existsById - return false when jobFairEmployerParticipation object does not exist")
    public void testExistsByIdDoesNotExist() {
        when(jobFairEmployerParticipationRepository.existsById(jobFairEmployerParticipation1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(jobFairEmployerParticipation1.getId(), ENTITY_NAME);

        assertFalse(jobFairEmployerParticipationService.existsById(jobFairEmployerParticipation1.getId()));
    }

    @Test
    @DisplayName("Test strictExistsById - do nothing when jobFairEmployerParticipation object exists")
    public void testStrictExistsByIdExists() {
        when(jobFairEmployerParticipationRepository.existsById(jobFairEmployerParticipation1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(jobFairEmployerParticipation1.getId(), ENTITY_NAME);

        assertDoesNotThrow(() -> jobFairEmployerParticipationService.strictExistsById(jobFairEmployerParticipation1.getId()));
    }

    @Test
    @DisplayName("Test strictExistsById - throw NoSuchElementFoundException when jobFairEmployerParticipation object does not exist")
    public void testStrictExistsByIdDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, jobFairEmployerParticipation1.getId());

        when(jobFairEmployerParticipationRepository.existsById(jobFairEmployerParticipation1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(jobFairEmployerParticipation1.getId(), ENTITY_NAME);

        try {
            jobFairEmployerParticipationService.strictExistsById(jobFairEmployerParticipation1.getId());
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
            jobFairEmployerParticipationService.strictExistsById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findAll - return all existing jobFairEmployerParticipation objects")
    public void testFindAll() {
        when(jobFairEmployerParticipationRepository.findAll()).thenReturn(List.of(jobFairEmployerParticipation1, jobFairEmployerParticipation2));
        when(jobFairEmployerParticipationMapper.toDto(jobFairEmployerParticipation1)).thenReturn(jobFairEmployerParticipationDto1);
        when(jobFairEmployerParticipationMapper.toDto(jobFairEmployerParticipation2)).thenReturn(jobFairEmployerParticipationDto2);

        Set<JobFairEmployerParticipationDto> result = jobFairEmployerParticipationService.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(jobFairEmployerParticipationDto1));
        assertTrue(result.contains(jobFairEmployerParticipationDto2));
    }

    @Test
    @DisplayName("Test findAll - return empty set when there are no jobFairEmployerParticipation objects")
    public void testFindAllEmpty() {
        when(jobFairEmployerParticipationRepository.findAll()).thenReturn(List.of());

        Set<JobFairEmployerParticipationDto> result = jobFairEmployerParticipationService.findAll();

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Test findById - return jobFairEmployerParticipation object when it exists")
    public void testFindById() {
        when(jobFairEmployerParticipationRepository.findById(jobFairEmployerParticipation1.getId())).thenReturn(java.util.Optional.of(jobFairEmployerParticipation1));
        when(jobFairEmployerParticipationMapper.toDto(jobFairEmployerParticipation1)).thenReturn(jobFairEmployerParticipationDto1);

        JobFairEmployerParticipationDto result = jobFairEmployerParticipationService.findById(jobFairEmployerParticipation1.getId());

        assertEquals(jobFairEmployerParticipationDto1, result);
    }

    @Test
    @DisplayName("Test findById - throw NoSuchElementFoundException when jobFairEmployerParticipation object does not exist")
    public void testFindByIdNullId() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, jobFairEmployerParticipation1.getId());
        when(jobFairEmployerParticipationRepository.findById(jobFairEmployerParticipation1.getId())).thenReturn(java.util.Optional.empty());

        try {
            jobFairEmployerParticipationService.findById(jobFairEmployerParticipation1.getId());
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
            jobFairEmployerParticipationService.findById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test save - return jobFairEmployerParticipation object when it is valid")
    public void testSaveValid() {
        when(jobFairEmployerParticipationRepository.save(jobFairEmployerParticipation1)).thenReturn(jobFairEmployerParticipation1);
        when(jobFairEmployerParticipationMapper.toDto(jobFairEmployerParticipation1)).thenReturn(jobFairEmployerParticipationDto1);
        doNothing().when(jobFairEmployerParticipationValidator).validate(jobFairEmployerParticipation1);

        JobFairEmployerParticipationDto result = jobFairEmployerParticipationService.save(jobFairEmployerParticipation1);

        assertEquals(jobFairEmployerParticipationDto1, result);
    }

    @Test
    @DisplayName("Test save - throw InvalidArgumentException when jobFairEmployerParticipation object is null")
    public void testSaveThrowExceptionNull() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        doThrow(new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME))).when(jobFairEmployerParticipationValidator).validate(null);

        try {
            jobFairEmployerParticipationService.save(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test save - throw InvalidArgumentException when jobFairEmployerParticipationValidator fails")
    public void testSaveThrowExceptionjobFairEmployerParticipationValidatorFails() {
        jobFairEmployerParticipation1.setJobFair(null);
        String expectedMessage = ExceptionMessages.nullArgument("jobFair");

        doThrow(new InvalidArgumentException(ExceptionMessages.nullArgument("jobFair"))).when(jobFairEmployerParticipationValidator).validate(jobFairEmployerParticipation1);

        try {
            jobFairEmployerParticipationService.save(jobFairEmployerParticipation1);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - jobFairEmployerParticipation object exists")
    public void testDeletejobFairEmployerParticipationExists() {
        when(jobFairEmployerParticipationRepository.existsById(jobFairEmployerParticipation1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(jobFairEmployerParticipation1.getId(), ENTITY_NAME);
        doNothing().when(jobFairEmployerParticipationRepository).delete(jobFairEmployerParticipation1);

        assertDoesNotThrow(() -> jobFairEmployerParticipationService.delete(jobFairEmployerParticipation1));
    }

    @Test
    @DisplayName("Test delete - jobFairEmployerParticipation object does not exist")
    public void testDeletejobFairEmployerParticipationDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, jobFairEmployerParticipation1.getId());
        when(jobFairEmployerParticipationRepository.existsById(jobFairEmployerParticipation1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(jobFairEmployerParticipation1.getId(), ENTITY_NAME);

        try {
            jobFairEmployerParticipationService.delete(jobFairEmployerParticipation1);
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - jobFairEmployerParticipation object is null")
    public void testDeletejobFairEmployerParticipationNull() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        try {
            jobFairEmployerParticipationService.delete(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - jobFairEmployerParticipation id is null")
    public void testDeletejobFairEmployerParticipationIdNull() {
        String expectedMessage = ExceptionMessages.idCannotBeNull(ENTITY_NAME);

        doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);
        jobFairEmployerParticipation1.setId(null);

        try {
            jobFairEmployerParticipationService.delete(jobFairEmployerParticipation1);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete by id - jobFairEmployerParticipation object exists")
    public void testDeleteByIdjobFairEmployerParticipationExists() {
        when(jobFairEmployerParticipationRepository.existsById(jobFairEmployerParticipation1.getId())).thenReturn(true);
        doNothing().when(idValidator).validateLongId(jobFairEmployerParticipation1.getId(), ENTITY_NAME);
        doNothing().when(jobFairEmployerParticipationRepository).deleteById(jobFairEmployerParticipation1.getId());

        assertDoesNotThrow(() -> jobFairEmployerParticipationService.deleteById(jobFairEmployerParticipation1.getId()));
    }

    @Test
    @DisplayName("Test delete by id - jobFairEmployerParticipation object does not exist")
    public void testDeleteByIdDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, jobFairEmployerParticipation1.getId());

        when(jobFairEmployerParticipationRepository.existsById(jobFairEmployerParticipation1.getId())).thenReturn(false);
        doNothing().when(idValidator).validateLongId(jobFairEmployerParticipation1.getId(), ENTITY_NAME);

        try {
            jobFairEmployerParticipationService.deleteById(jobFairEmployerParticipation1.getId());
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete by id - id is null")
    public void testDeleteByIdNull() {
        String expectedMessage = ExceptionMessages.idCannotBeNull(ENTITY_NAME);

        doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

        try {
            jobFairEmployerParticipationService.deleteById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test update - update and return jobFairEmployerParticipationDto object when it is valid")
    public void testUpdateJobFairEmployerParticipationExists() {
        doNothing().when(idValidator).validateLongId(jobFairEmployerParticipation1.getId(), ENTITY_NAME);
        when(jobFairEmployerParticipationRepository.existsById(jobFairEmployerParticipation1.getId())).thenReturn(true);
        doNothing().when(jobFairEmployerParticipationValidator).validateDto(jobFairEmployerParticipationDto1);
        when(jobFairEmployerParticipationMapper.toEntity(jobFairEmployerParticipationDto1)).thenReturn(jobFairEmployerParticipation1);
        when(jobFairEmployerParticipationRepository.save(jobFairEmployerParticipation1)).thenReturn(jobFairEmployerParticipation1);
        when(jobFairEmployerParticipationMapper.toDto(jobFairEmployerParticipation1)).thenReturn(jobFairEmployerParticipationDto1);

        JobFairEmployerParticipationDto result = jobFairEmployerParticipationService.update(jobFairEmployerParticipationDto1.getId(), jobFairEmployerParticipationDto1);

        assertEquals(jobFairEmployerParticipationDto1, result);
    }

    @Test
    @DisplayName("Test update - throw NoSuchElementFoundException when IdValidator fails")
    public void testUpdateJobFairEmployerParticipationDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, jobFairEmployerParticipation1.getId());
        doThrow(new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, jobFairEmployerParticipation1.getId()))).when(idValidator).validateLongId(jobFairEmployerParticipation1.getId(), ENTITY_NAME);

        try {
            jobFairEmployerParticipationService.update(jobFairEmployerParticipation1.getId(), jobFairEmployerParticipationDto1);
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test update - throw InvalidArgumentException when jobFairEmployerParticipationDto object is null and jobFairEmployerParticipationValidator fails")
    public void testUpdateJobFairEmployerParticipationDtoNull() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        when(jobFairEmployerParticipationRepository.existsById(jobFairEmployerParticipation1.getId())).thenReturn(true);
        doThrow(new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME))).when(jobFairEmployerParticipationValidator).validateDto(null);

        try {
            jobFairEmployerParticipationService.update(jobFairEmployerParticipation1.getId(), null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @DisplayName("Test createParticipationRequestByEmployer - create and return jobFairEmployerParticipationDto object when it is valid")
    public void testCreateParticipationRequestByEmployerValid() {
        when(employerService.getAuthEmployer()).thenReturn(jobFairEmployerParticipation1.getEmployer());
        when(jobFairService.getOnlyPaidJobFairById(jobFairEmployerParticipation1.getJobFair().getId())).thenReturn(jobFairEmployerParticipation1.getJobFair());
        doNothing().when(jobFairEmployerParticipationValidator).validate(any());
        when(jobFairEmployerParticipationRepository.save(any())).thenReturn(jobFairEmployerParticipation1);
        when(jobFairEmployerParticipationMapper.toDto(jobFairEmployerParticipation1)).thenReturn(jobFairEmployerParticipationDto1);
        when(jobFairService.getContactEmail(jobFairEmployerParticipation1.getJobFair().getId())).thenReturn(organizerContactEmail);

        JobFairEmployerParticipationDto result = jobFairEmployerParticipationService.createParticipationRequestByEmployer(jobFairEmployerParticipationDto1.getJobFairId());

        assertEquals(jobFairEmployerParticipationDto1, result);
        verify(eMailService, times(1)).sendHtmlMessageWithTemplate(anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("Test createParticipationRequestByEmployer - JobFairEmployerParticipationValidator fails (jobFairEmployerParticipationDto object is null)")
    public void testCreateParticipationRequestByEmployerNull() {
        String expectedMessage = ExceptionMessages.nullArgument(ENTITY_NAME);

        when(employerService.getAuthEmployer()).thenReturn(jobFairEmployerParticipation1.getEmployer());
        when(jobFairService.getOnlyPaidJobFairById(jobFairEmployerParticipation1.getJobFair().getId())).thenReturn(jobFairEmployerParticipation1.getJobFair());
        doThrow(new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME))).when(jobFairEmployerParticipationValidator).validate(any());

        try {
            jobFairEmployerParticipationService.createParticipationRequestByEmployer(jobFairEmployerParticipationDto1.getJobFairId());
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
            verify(eMailService, times(0)).sendHtmlMessageWithTemplate(anyString(), anyString(), anyString());
        }
    }

    @Test
    @DisplayName("Test createParticipationRequestByEmployer - EmployerService fails (employer object is null)")
    public void testCreateParticipationRequestByEmployerEmployerServiceFails() {
        String expectedMessage = ExceptionMessages.unauthorized("employer");

        doThrow(new AuthException(expectedMessage)).when(employerService).getAuthEmployer();

        try {
            jobFairEmployerParticipationService.createParticipationRequestByEmployer(jobFairEmployerParticipationDto1.getJobFairId());
            fail();
        } catch (AuthException e) {
            assertEquals(expectedMessage, e.getMessage());
            verify(eMailService, times(0)).sendHtmlMessageWithTemplate(anyString(), anyString(), anyString());
        }
    }

    @Test
    @DisplayName("Test createParticipationRequestByEmployer - JobFairService fails (jobFair object is null)")
    public void testCreateParticipationRequestByEmployerJobFairServiceFails() {
        String expectedMessage = ExceptionMessages.elementNotFound("JobFair", jobFairEmployerParticipationDto1.getJobFairId());

        when(employerService.getAuthEmployer()).thenReturn(jobFairEmployerParticipation1.getEmployer());
        doThrow(new NoSuchElementFoundException(expectedMessage)).when(jobFairService).getOnlyPaidJobFairById(jobFairEmployerParticipationDto1.getJobFairId());

        try {
            jobFairEmployerParticipationService.createParticipationRequestByEmployer(jobFairEmployerParticipationDto1.getJobFairId());
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
            verify(eMailService, times(0)).sendHtmlMessageWithTemplate(anyString(), anyString(), anyString());
        }
    }

    @Test
    @DisplayName("Test acceptParticipationRequest - accept and return jobFairEmployerParticipationDto object when it is valid")
    public void testAcceptParticipationRequestValid() {
        doNothing().when(idValidator).validateLongId(jobFairEmployerParticipation1.getId(), ENTITY_NAME);
        when(jobFairEmployerParticipationRepository.findById(jobFairEmployerParticipation1.getId())).thenReturn(Optional.of(jobFairEmployerParticipation1));
        when(jobFairEmployerParticipationMapper.toDto(jobFairEmployerParticipation1)).thenReturn(jobFairEmployerParticipationDto1);
        doNothing().when(jobFairEmployerParticipationValidator).validateDto(any());
        when(jobFairEmployerParticipationRepository.existsById(jobFairEmployerParticipation1.getId())).thenReturn(true);
        when(jobFairEmployerParticipationRepository.save(any())).thenReturn(jobFairEmployerParticipation1);
        when(jobFairService.getJobFairById(jobFairEmployerParticipation1.getJobFair().getId())).thenReturn(jobFairEmployerParticipation1.getJobFair());
        when(employerService.findById(jobFairEmployerParticipation1.getEmployer().getId())).thenReturn(jobFairEmployerParticipation1.getEmployer());

        JobFairEmployerParticipationDto result = jobFairEmployerParticipationService.acceptParticipationRequestByOrganizer(jobFairEmployerParticipation1.getId());

        assertEquals(jobFairEmployerParticipationDto1, result);
        verify(eMailService, times(1)).sendHtmlMessageWithTemplate(anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("Test acceptParticipationRequest - fail when JobFairEmployerParticipation with given id does not exist")
    public void testAcceptParticipationRequestDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, jobFairEmployerParticipation1.getId());

        doThrow(new NoSuchElementFoundException(expectedMessage)).when(jobFairEmployerParticipationRepository).findById(jobFairEmployerParticipation1.getId());

        try {
            jobFairEmployerParticipationService.acceptParticipationRequestByOrganizer(jobFairEmployerParticipation1.getId());
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
            verify(eMailService, times(0)).sendHtmlMessageWithTemplate(anyString(), anyString(), anyString());
        }
    }

    @Test
    @DisplayName("Test rejectParticipationRequestByOrganizer - reject and delete JobFairEmployerParticipation with given id")
    public void testRejectParticipationRequestByOrganizerValid() {
        doNothing().when(idValidator).validateLongId(jobFairEmployerParticipation1.getId(), ENTITY_NAME);
        when(jobFairEmployerParticipationRepository.findById(jobFairEmployerParticipation1.getId())).thenReturn(Optional.of(jobFairEmployerParticipation1));
        when(jobFairEmployerParticipationRepository.existsById(jobFairEmployerParticipation1.getId())).thenReturn(true);
        doNothing().when(jobFairEmployerParticipationRepository).deleteById(jobFairEmployerParticipation1.getId());
        doNothing().when(eMailService).sendHtmlMessageWithTemplate(anyString(), anyString(), anyString());

        assertDoesNotThrow(() -> jobFairEmployerParticipationService.rejectParticipationRequestByOrganizer(jobFairEmployerParticipation1.getId()));
        verify(eMailService, times(1)).sendHtmlMessageWithTemplate(anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("Test rejectParticipationRequestByOrganizer - fail when JobFairEmployerParticipation with given id does not exist")
    public void testRejectParticipationRequestByOrganizerDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, jobFairEmployerParticipation1.getId());

        doThrow(new NoSuchElementFoundException(expectedMessage)).when(jobFairEmployerParticipationRepository).findById(jobFairEmployerParticipation1.getId());

        try {
            jobFairEmployerParticipationService.rejectParticipationRequestByOrganizer(jobFairEmployerParticipation1.getId());
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
            verify(eMailService, times(0)).sendHtmlMessageWithTemplate(anyString(), anyString(), anyString());
        }
    }

    @Test
    @DisplayName("Test deleteParticipationRequestByOrganizer - delete JobFairEmployerParticipation with given id")
    public void testDeleteParticipationRequestByOrganizerValid() {
        doNothing().when(idValidator).validateLongId(jobFairEmployerParticipation1.getId(), ENTITY_NAME);
        when(jobFairEmployerParticipationRepository.findById(jobFairEmployerParticipation1.getId())).thenReturn(Optional.of(jobFairEmployerParticipation1));
        when(jobFairEmployerParticipationRepository.existsById(jobFairEmployerParticipation1.getId())).thenReturn(true);
        doNothing().when(jobFairEmployerParticipationRepository).deleteById(jobFairEmployerParticipation1.getId());
        doNothing().when(eMailService).sendHtmlMessageWithTemplate(anyString(), anyString(), anyString());

        assertDoesNotThrow(() -> jobFairEmployerParticipationService.deleteParticipationRequestByOrganizer(jobFairEmployerParticipation1.getId()));
        verify(eMailService, times(1)).sendHtmlMessageWithTemplate(anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("Test deleteParticipationRequestByOrganizer - fail when JobFairEmployerParticipation with given id does not exist")
    public void testDeleteParticipationRequestByOrganizerDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, jobFairEmployerParticipation1.getId());

        doThrow(new NoSuchElementFoundException(expectedMessage)).when(jobFairEmployerParticipationRepository).findById(jobFairEmployerParticipation1.getId());

        try {
            jobFairEmployerParticipationService.deleteParticipationRequestByOrganizer(jobFairEmployerParticipation1.getId());
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
            verify(eMailService, times(0)).sendHtmlMessageWithTemplate(anyString(), anyString(), anyString());
        }
    }

    @Test
    @DisplayName("Test deleteParticipationRequestByEmployer - delete JobFairEmployerParticipation with given id")
    public void testDeleteParticipationRequestByEmployerValid() {
        doNothing().when(idValidator).validateLongId(jobFairEmployerParticipation1.getId(), ENTITY_NAME);
        when(jobFairEmployerParticipationRepository.findById(jobFairEmployerParticipation1.getId())).thenReturn(Optional.of(jobFairEmployerParticipation1));
        when(jobFairEmployerParticipationRepository.existsById(jobFairEmployerParticipation1.getId())).thenReturn(true);
        doNothing().when(jobFairEmployerParticipationRepository).deleteById(jobFairEmployerParticipation1.getId());
        doNothing().when(eMailService).sendHtmlMessageWithTemplate(anyString(), anyString(), anyString());
        when(jobFairService.getContactEmail(jobFairEmployerParticipation1.getJobFair().getId())).thenReturn(organizerContactEmail);

        assertDoesNotThrow(() -> jobFairEmployerParticipationService.deleteParticipationRequestByEmployer(jobFairEmployerParticipation1.getId()));
        verify(eMailService, times(1)).sendHtmlMessageWithTemplate(anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("Test deleteParticipationRequestByEmployer - fail when JobFairEmployerParticipation with given id does not exist")
    public void testDeleteParticipationRequestByEmployerDoesNotExist() {
        String expectedMessage = ExceptionMessages.elementNotFound(ENTITY_NAME, jobFairEmployerParticipation1.getId());

        doThrow(new NoSuchElementFoundException(expectedMessage)).when(jobFairEmployerParticipationRepository).findById(jobFairEmployerParticipation1.getId());

        try {
            jobFairEmployerParticipationService.deleteParticipationRequestByEmployer(jobFairEmployerParticipation1.getId());
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(expectedMessage, e.getMessage());
            verify(eMailService, times(0)).sendHtmlMessageWithTemplate(anyString(), anyString(), anyString());
        }
    }

}
