package miwm.job4me.model.offer;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import miwm.job4me.model.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "requirements")
public class Requirement extends BaseEntity {

    @Builder
    public Requirement(Long id, String description, JobOffer jobOffer) {
        super(id);
        this.description = description;
        this.jobOffer = jobOffer;
    }

    @NotBlank(message = "Requirement description cannot be blank")
    @Size(min = 1, max = 250, message = "Requirement description must be between 1 and 250 characters")
    @Column(name = "description", length = 250)
    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_offer_id")
    private JobOffer jobOffer;

}
