package miwm.job4me.model.offer;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import miwm.job4me.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "employment_forms")
public class EmploymentForm extends BaseEntity {

    @Builder
    public EmploymentForm(Long id, String form, Set<JobOffer> jobOffers) {
        super(id);
        this.form = form;
        this.jobOffers = jobOffers;
    }

    @Column(name = "form", length = 20)
    private String form;

    @ManyToMany(mappedBy = "employmentForms")
    private Set<JobOffer> jobOffers;

}
