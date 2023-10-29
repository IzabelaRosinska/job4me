package miwm.job4me.model.offer;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import miwm.job4me.model.BaseEntity;

import javax.persistence.*;

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

    @Column(name = "description", length = 200)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_offer_id")
    private JobOffer jobOffer;

}
