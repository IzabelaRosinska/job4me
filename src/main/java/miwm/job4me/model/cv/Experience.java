package miwm.job4me.model.cv;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import miwm.job4me.model.users.Employee;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "experience")
public class Experience extends MultiEntrySection {

    @Builder
    public Experience(Long id, String description, Employee employee) {
        super(id, description, employee);
    }
}
