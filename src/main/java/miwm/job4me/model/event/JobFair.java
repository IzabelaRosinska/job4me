package miwm.job4me.model.event;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import miwm.job4me.model.BaseEntity;
import miwm.job4me.model.payment.Payment;
import miwm.job4me.model.users.Organizer;
import miwm.job4me.validators.fields.ValidDateRange;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ValidDateRange
@Entity
@Table(name = "job_fairs")
public class JobFair extends BaseEntity {
    @Builder
    public JobFair(Long id, String name, Organizer organizer, LocalDateTime dateStart, LocalDateTime dateEnd, String address, String description, String displayDescription, String photo, Payment payment, Set<JobFairEmployerParticipation> jobFairEmployerParticipation) {
        super(id);
        this.name = name;
        this.organizer = organizer;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.address = address;
        this.description = description;
        this.displayDescription = displayDescription;
        this.photo = photo;
        this.payment = payment;
        this.jobFairEmployerParticipation = jobFairEmployerParticipation;
    }

    @NotBlank
    @Size(min = 1, max = 100, message = "JobFair name must be between 1 and 100 characters")
    @Column(name = "name", length = 100)
    private String name;

    @NotNull(message = "JobFair organizer cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id")
    private Organizer organizer;

    @NotNull(message = "JobFair dateStart cannot be null")
    @Column(name = "date_start")
    private LocalDateTime dateStart;

    @NotNull(message = "JobFair dateEnd cannot be null")
    @Column(name = "date_end")
    private LocalDateTime dateEnd;

    @NotBlank(message = "JobFair address cannot be blank")
    @Size(min = 1, max = 200, message = "JobFair address must be between 1 and 200 characters")
    @Column(name = "address", length = 200)
    private String address;

    @NotBlank(message = "JobFair description cannot be blank")
    @Size(min = 1, max = 1000, message = "JobFair description must be between 1 and 1000 characters")
    @Column(name = "description", length = 1000)
    private String description;

    @NotBlank(message = "JobFair displayDescription cannot be blank")
    @Size(min = 1, max = 150, message = "JobFair displayDescription must be between 1 and 150 characters")
    @Column(name = "display_description", length = 150)
    private String displayDescription;

    @Size(max = 13000, message = "JobFair photo must be less than 13000 characters")
    @Column(name = "photo", length = 13000)
    private String photo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jobFair")
    private Set<JobFairEmployerParticipation> jobFairEmployerParticipation;

}
