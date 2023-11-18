package miwm.job4me.web.mappers.event;

import miwm.job4me.model.event.JobFair;
import miwm.job4me.model.event.JobFairEmployerParticipation;
import miwm.job4me.model.users.Employer;
import miwm.job4me.web.model.event.JobFairEmployerParticipationDto;
import org.springframework.stereotype.Component;

@Component
public class JobFairEmployerParticipationMapper {
    public JobFairEmployerParticipationDto toDto(JobFairEmployerParticipation jobFairEmployerParticipation) {
        JobFairEmployerParticipationDto jobFairEmployerParticipationDto = new JobFairEmployerParticipationDto();
        jobFairEmployerParticipationDto.setId(jobFairEmployerParticipation.getId());
        jobFairEmployerParticipationDto.setJobFairId(jobFairEmployerParticipation.getJobFair().getId());
        jobFairEmployerParticipationDto.setEmployerId(jobFairEmployerParticipation.getEmployer().getId());
        jobFairEmployerParticipationDto.setAccepted(jobFairEmployerParticipation.isAccepted());
        return jobFairEmployerParticipationDto;
    }

    public JobFairEmployerParticipation toEntity(JobFairEmployerParticipationDto jobFairEmployerParticipationDto) {
        JobFairEmployerParticipation jobFairEmployerParticipation = new JobFairEmployerParticipation();
        jobFairEmployerParticipation.setId(jobFairEmployerParticipationDto.getId());
        jobFairEmployerParticipation.setJobFair(JobFair.builder().id(jobFairEmployerParticipationDto.getJobFairId()).build());
        jobFairEmployerParticipation.setEmployer(Employer.builder().id(jobFairEmployerParticipationDto.getEmployerId()).build());
        jobFairEmployerParticipation.setAccepted(jobFairEmployerParticipationDto.isAccepted());
        return jobFairEmployerParticipation;
    }
}
