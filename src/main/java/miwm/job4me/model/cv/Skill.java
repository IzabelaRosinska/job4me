package miwm.job4me.model.cv;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import miwm.job4me.model.users.Employee;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "skills")
@AttributeOverrides(
        @AttributeOverride(name = "description", column = @Column(length = 50))
)
public class Skill extends MultiEntrySection {

    @Builder
    public Skill(Long id, String description, Employee employee) {
        super(id, description, employee);
    }
}
