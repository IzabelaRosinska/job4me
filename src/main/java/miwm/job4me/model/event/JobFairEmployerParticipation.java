package miwm.job4me.model.event;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import miwm.job4me.model.BaseEntity;
import miwm.job4me.model.users.Employer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "job_fair_employer_participation")
public class JobFairEmployerParticipation extends BaseEntity {
    @Builder
    public JobFairEmployerParticipation(Long id, JobFair jobFair, Employer employer) {
        super(id);
        this.jobFair = jobFair;
        this.employer = employer;
    }

    @NotNull(message = "JobFairEmployerParticipation jobFairId cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_fair_id")
    private JobFair jobFair;

    @NotNull(message = "JobFairEmployerParticipation employerId cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employer_id")
    private Employer employer;

}
