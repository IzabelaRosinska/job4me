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
@Table(name = "education")
public class Education extends BaseEntity {

    @Builder
    public Education(Long id, String description, Employee employee) {
        super(id);
        this.description = description;
        this.employee = employee;
    }

    @NotBlank(message = "Education description cannot be blank")
    @Size(min = 1, max = 100, message = "Education description must be between 1 and 100 characters")
    @Column(name = "description", length = 100)
    private String description;

    @NotNull(message = "Education employee cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
