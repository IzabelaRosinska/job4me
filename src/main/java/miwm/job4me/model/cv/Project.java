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
@Table(name = "projects")
@AttributeOverrides(
        @AttributeOverride(name = "description", column = @Column(length = 500))
)
public class Project extends MultiEntrySection {

    @Builder
    public Project(Long id, String description, Employee employee) {
        super(id, description, employee);
    }
}
