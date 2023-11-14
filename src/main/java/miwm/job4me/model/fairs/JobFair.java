package miwm.job4me.model.fairs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import miwm.job4me.model.BaseEntity;
import miwm.job4me.model.users.Organizer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "job_fairs")
public class JobFair extends BaseEntity {

    public JobFair(Long id) {
        super(id);
    }

    @NotNull(message = "Organizer cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id")
    private Organizer organizer;
}
