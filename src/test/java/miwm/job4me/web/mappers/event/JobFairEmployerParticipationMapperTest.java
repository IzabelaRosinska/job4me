package miwm.job4me.web.mappers.event;

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

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class JobFairEmployerParticipationMapperTest {
    @InjectMocks
    private JobFairEmployerParticipationMapper jobFairEmployerParticipationMapper;

    private JobFairEmployerParticipation jobFairEmployerParticipation;

    private JobFairEmployerParticipationDto jobFairEmployerParticipationDto;

    private final Long ID = 1L;

    @BeforeEach
    void setUp() {
        Employer employer = Employer.builder().id(ID).build();
        JobFair jobFair = JobFair.builder().id(ID).build();

        jobFairEmployerParticipation = JobFairEmployerParticipation.builder()
                .id(ID)
                .employer(employer)
                .jobFair(jobFair)
                .isAccepted(true)
                .build();

        jobFairEmployerParticipationDto = new JobFairEmployerParticipationDto();
        jobFairEmployerParticipationDto.setId(jobFairEmployerParticipation.getId());
        jobFairEmployerParticipationDto.setEmployerId(jobFairEmployerParticipation.getEmployer().getId());
        jobFairEmployerParticipationDto.setJobFairId(jobFairEmployerParticipation.getJobFair().getId());
        jobFairEmployerParticipationDto.setIsAccepted(jobFairEmployerParticipation.getIsAccepted());
    }

    @Test
    @DisplayName("test toDto - should return dto with the same values as entity")
    void toDto() {
        JobFairEmployerParticipationDto jobFairEmployerParticipationDtoResult = jobFairEmployerParticipationMapper.toDto(jobFairEmployerParticipation);

        assertEquals(jobFairEmployerParticipationDto.getId(), jobFairEmployerParticipationDtoResult.getId());
        assertEquals(jobFairEmployerParticipationDto.getEmployerId(), jobFairEmployerParticipationDtoResult.getEmployerId());
        assertEquals(jobFairEmployerParticipationDto.getJobFairId(), jobFairEmployerParticipationDtoResult.getJobFairId());
        assertEquals(jobFairEmployerParticipationDto.getIsAccepted(), jobFairEmployerParticipationDtoResult.getIsAccepted());
    }

    @Test
    @DisplayName("test toEntity - should return entity with the same values as dto")
    void toEntity() {
        JobFairEmployerParticipation jobFairEmployerParticipationResult = jobFairEmployerParticipationMapper.toEntity(jobFairEmployerParticipationDto);

        assertEquals(jobFairEmployerParticipation.getId(), jobFairEmployerParticipationResult.getId());
        assertEquals(jobFairEmployerParticipation.getEmployer().getId(), jobFairEmployerParticipationResult.getEmployer().getId());
        assertEquals(jobFairEmployerParticipation.getJobFair().getId(), jobFairEmployerParticipationResult.getJobFair().getId());
        assertEquals(jobFairEmployerParticipation.getIsAccepted(), jobFairEmployerParticipationResult.getIsAccepted());
    }

}
