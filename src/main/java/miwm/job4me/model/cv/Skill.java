package miwm.job4me.model.cv;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import miwm.job4me.model.BaseEntity;
import miwm.job4me.model.users.Employee;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "skills")
public class Skill extends BaseEntity {

    @Builder
    public Skill(Long id, String description, Employee employee) {
        super(id);
        this.description = description;
        this.employee = employee;
    }

    @NotBlank(message = "Skill description cannot be blank")
    @Size(min = 1, max = 50, message = "Skill description must be between 1 and 50 characters")
    @Column(name = "description", length = 50)
    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
