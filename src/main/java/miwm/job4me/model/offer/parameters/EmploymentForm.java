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
@Table(name = "employment_forms")
public class EmploymentForm extends BaseEntity {

    @Builder
    public EmploymentForm(Long id, String name, Set<JobOffer> jobOffers) {
        super(id);
        this.name = name;
        this.jobOffers = jobOffers;
    }

    @NotBlank
    @Size(min = 1, max = 25, message = "EmploymentForm name must be between 1 and 25 characters")
    @Column(name = "name", length = 25)
    private String name;

    @ManyToMany(mappedBy = "employmentForms")
    private Set<JobOffer> jobOffers;

}
