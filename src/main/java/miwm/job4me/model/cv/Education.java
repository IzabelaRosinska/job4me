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
@Table(name = "education")
@AttributeOverrides(
        @AttributeOverride(name = "description", column = @Column(length = 100))
)
public class Education extends MultiEntrySection {

    @Builder
    public Education(Long id, String description, Employee employee) {
        super(id, description, employee);
    }
}