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
@Table(name = "industries")
public class Industry extends BaseEntity {

    @Builder
    public Industry(Long id, String name) {
        super(id);
        this.name = name;
    }

    @Column(name = "name", length = 100)
    private String name;

    @ManyToMany(mappedBy = "industries")
    private Set<JobOffer> jobOffers;

}
