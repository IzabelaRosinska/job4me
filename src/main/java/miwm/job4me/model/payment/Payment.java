package miwm.job4me.model.payment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import miwm.job4me.model.BaseEntity;
import miwm.job4me.model.event.JobFair;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class Payment extends BaseEntity {

    @Builder
    public Payment(Long id, String sessionId, Boolean isPaid, LocalDateTime creationTimestamp, JobFair jobFair) {
        super(id);
        this.sessionId = sessionId;
        this.isPaid = isPaid;
        this.creationTimestamp = creationTimestamp;
        this.jobFair = jobFair;
    }

    @NotNull(message = "Payment session id cannot be null")
    @Column(name = "session_id")
    private String sessionId;

    @NotNull(message = "Payment is paid cannot be null")
    @Column(name = "is_paid")
    private Boolean isPaid;

    @NotNull(message = "Payment creation timestamp cannot be null")
    @Column(name = "creation_timestamp")
    private LocalDateTime creationTimestamp;

    @NotNull(message = "Payment job fair cannot be null")
    @OneToOne(mappedBy = "payment")
    private JobFair jobFair;

}
