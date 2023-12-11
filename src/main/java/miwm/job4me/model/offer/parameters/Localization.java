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
@Table(name = "localizations")
public class Localization extends BaseEntity {

    @Builder
    public Localization(Long id, String city, Set<JobOffer> jobOffers) {
        super(id);
        this.city = city;
        this.jobOffers = jobOffers;
    }

    @NotBlank(message = "Localization city cannot be blank")
    @Size(min = 1, max = 50, message = "Localization city must be between 1 and 50 characters")
    @Column(name = "city", length = 50)
    private String city;

    @ManyToMany(mappedBy = "localizations")
    private Set<JobOffer> jobOffers;
}
