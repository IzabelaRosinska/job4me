package miwm.job4me.model.offer.parameters;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import miwm.job4me.model.BaseEntity;
import miwm.job4me.model.offer.JobOffer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "levels")
public class Level extends BaseEntity {

    @Builder
    public Level(Long id, String name, Set<JobOffer> jobOffers) {
        super(id);
        this.name = name;
        this.jobOffers = jobOffers;
    }

    @NotBlank(message = "Level name cannot be blank")
    @Size(min = 1, max = 20, message = "Level name must be between 1 and 20 characters")
    @Column(name = "name", length = 20)
    private String name;

    @ManyToMany(mappedBy = "levels")
    private Set<JobOffer> jobOffers;

}
