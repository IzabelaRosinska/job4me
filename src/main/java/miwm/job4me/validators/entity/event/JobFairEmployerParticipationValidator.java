package miwm.job4me.validators.entity.event;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.event.JobFairEmployerParticipation;
import miwm.job4me.web.model.event.JobFairEmployerParticipationDto;
import org.springframework.stereotype.Component;

@Component
public class JobFairEmployerParticipationValidator {
    private final String ENTITY_NAME = "JobFairEmployerParticipation";
    private final String JOB_FAIR_ENTITY_NAME = "JobFair";
    private final String EMPLOYER_ENTITY_NAME = "Employer";

    public void validate(JobFairEmployerParticipation jobFairEmployerParticipation) {
        if (jobFairEmployerParticipation == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        if (jobFairEmployerParticipation.getJobFair() == null) {
            throw new InvalidArgumentException(ExceptionMessages.notNull(ENTITY_NAME, JOB_FAIR_ENTITY_NAME));
        }

        if (jobFairEmployerParticipation.getEmployer() == null) {
            throw new InvalidArgumentException(ExceptionMessages.notNull(ENTITY_NAME, EMPLOYER_ENTITY_NAME));
        }

        if (jobFairEmployerParticipation.isAccepted()) {
            throw new InvalidArgumentException(ExceptionMessages.notNull(ENTITY_NAME, "isAccepted"));
        }

    }

    public void validateDto(JobFairEmployerParticipationDto jobFairEmployerParticipation) {
        if (jobFairEmployerParticipation == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        if (jobFairEmployerParticipation.getJobFairId() == null) {
            throw new InvalidArgumentException(ExceptionMessages.notNull(ENTITY_NAME, JOB_FAIR_ENTITY_NAME));
        }

        if (jobFairEmployerParticipation.getEmployerId() == null) {
            throw new InvalidArgumentException(ExceptionMessages.notNull(ENTITY_NAME, EMPLOYER_ENTITY_NAME));
        }

        if (jobFairEmployerParticipation.isAccepted()) {
            throw new InvalidArgumentException(ExceptionMessages.notNull(ENTITY_NAME, "isAccepted"));
        }
    }
}
