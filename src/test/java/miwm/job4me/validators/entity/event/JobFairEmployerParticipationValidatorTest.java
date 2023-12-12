package miwm.job4me.validators.entity.event;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.event.JobFair;
import miwm.job4me.model.event.JobFairEmployerParticipation;
import miwm.job4me.model.users.Employer;
import miwm.job4me.web.model.event.JobFairEmployerParticipationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JobFairEmployerParticipationValidatorTest {
    @InjectMocks
    private JobFairEmployerParticipationValidator jobFairEmployerParticipationValidator;

    private JobFairEmployerParticipation jobFairEmployerParticipation;
    private JobFairEmployerParticipationDto jobFairEmployerParticipationDto;

    private final String ENTITY_NAME = "JobFairEmployerParticipation";
    private final String JOB_FAIR_ENTITY_NAME = "JobFair";
    private final String EMPLOYER_ENTITY_NAME = "Employer";
    private final Long ID = 1L;

    @BeforeEach
    void setUp() {
        JobFair jobFair = JobFair.builder()
                .id(ID)
                .build();

        Employer employer = Employer.builder()
                .id(ID)
                .build();

        jobFairEmployerParticipation = JobFairEmployerParticipation.builder()
                .id(ID)
                .jobFair(jobFair)
                .employer(employer)
                .build();

        jobFairEmployerParticipationDto = new JobFairEmployerParticipationDto();
        jobFairEmployerParticipationDto.setJobFairId(jobFairEmployerParticipation.getId());
        jobFairEmployerParticipationDto.setJobFairId(jobFairEmployerParticipation.getJobFair().getId());
        jobFairEmployerParticipationDto.setEmployerId(jobFairEmployerParticipation.getEmployer().getId());
    }

    @Test
    @DisplayName("test validate - pass when JobFairEmployerParticipation is valid")
    void testValidatePassValidJobFairEmployerParticipation() {
        assertDoesNotThrow(() -> jobFairEmployerParticipationValidator.validate(jobFairEmployerParticipation));
    }

    @Test
    @DisplayName("test validate - throw InvalidArgumentException when JobFairEmployerParticipation is null")
    void testValidateThrowInvalidArgumentExceptionWhenJobFairEmployerParticipationIsNull() {
        try {
            jobFairEmployerParticipationValidator.validate(null);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - throw InvalidArgumentException when JobFairEmployerParticipation.jobFair is null")
    void testValidateThrowInvalidArgumentExceptionWhenJobFairEmployerParticipationJobFairIsNull() {
        jobFairEmployerParticipation.setJobFair(null);

        try {
            jobFairEmployerParticipationValidator.validate(jobFairEmployerParticipation);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNull(ENTITY_NAME, JOB_FAIR_ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - throw InvalidArgumentException when JobFairEmployerParticipation.employer is null")
    void testValidateThrowInvalidArgumentExceptionWhenJobFairEmployerParticipationEmployerIsNull() {
        jobFairEmployerParticipation.setEmployer(null);

        try {
            jobFairEmployerParticipationValidator.validate(jobFairEmployerParticipation);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNull(ENTITY_NAME, EMPLOYER_ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateDto - pass when JobFairEmployerParticipationDto is valid")
    void testValidateDtoPassValidJobFairEmployerParticipationDto() {
        assertDoesNotThrow(() -> jobFairEmployerParticipationValidator.validateDto(jobFairEmployerParticipationDto));
    }

    @Test
    @DisplayName("test validateDto - throw InvalidArgumentException when JobFairEmployerParticipationDto is null")
    void testValidateDtoThrowInvalidArgumentExceptionWhenJobFairEmployerParticipationDtoIsNull() {
        try {
            jobFairEmployerParticipationValidator.validateDto(null);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateDto - throw InvalidArgumentException when JobFairEmployerParticipationDto.jobFairId is null")
    void testValidateDtoThrowInvalidArgumentExceptionWhenJobFairEmployerParticipationDtoJobFairIdIsNull() {
        jobFairEmployerParticipationDto.setJobFairId(null);

        try {
            jobFairEmployerParticipationValidator.validateDto(jobFairEmployerParticipationDto);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNull(ENTITY_NAME, JOB_FAIR_ENTITY_NAME), e.getMessage());
        }
    }

}
