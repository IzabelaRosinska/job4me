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
@Table(name = "experience")
public class Experience extends BaseEntity {

    @Builder
    public Experience(Long id, String description, Employee employee) {
        super(id);
        this.description = description;
        this.employee = employee;
    }

    @NotBlank(message = "Experience description cannot be blank")
    @Size(min = 1, max = 300, message = "Experience description must be between 1 and 300 characters")
    @Column(name = "description")
    private String description;

    @NotNull(message = "Experience employee cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
