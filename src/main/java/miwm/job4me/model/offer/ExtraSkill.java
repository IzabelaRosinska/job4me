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
@Table(name = "extra_skills")
public class ExtraSkill extends BaseEntity {

    @Builder
    public ExtraSkill(Long id, String description, JobOffer jobOffer) {
        super(id);
        this.description = description;
        this.jobOffer = jobOffer;
    }

    @NotBlank(message = "ExtraSkill description cannot be blank")
    @Size(min = 1, max = 200, message = "ExtraSkill description must be between 1 and 200 characters")
    @Column(name = "description", length = 200)
    private String description;

    @NotNull(message = "ExtraSkill jobOffer cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_offer_id")
    private JobOffer jobOffer;

}
