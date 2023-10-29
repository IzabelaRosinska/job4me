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
@Table(name = "contract_types")
public class ContractType extends BaseEntity {

    @Builder
    public ContractType(Long id, String name, Set<JobOffer> jobOffers) {
        super(id);
        this.name = name;
        this.jobOffers = jobOffers;
    }

    @Column(name = "name", length = 20)
    private String name;

    @ManyToMany(mappedBy = "contractTypes")
    private Set<JobOffer> jobOffers;

}
