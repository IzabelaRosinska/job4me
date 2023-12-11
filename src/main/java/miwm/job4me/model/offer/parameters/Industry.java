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
@Table(name = "industries")
public class Industry extends BaseEntity {

    @Builder
    public Industry(Long id, String name) {
        super(id);
        this.name = name;
    }

    @NotBlank(message = "Industry name cannot be blank")
    @Size(min = 1, max = 100, message = "Industry name must be between 1 and 100 characters")
    @Column(name = "name", length = 100)
    private String name;

    @ManyToMany(mappedBy = "industries")
    private Set<JobOffer> jobOffers;

}
