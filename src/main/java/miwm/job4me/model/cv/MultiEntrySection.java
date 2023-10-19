package miwm.job4me.model.cv;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import miwm.job4me.model.BaseEntity;
import miwm.job4me.model.users.Employee;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class MultiEntrySection extends BaseEntity {

    public MultiEntrySection(Long id, String description, Employee employee) {
        super(id);
        this.description = description;
        this.employee = employee;
    }

    @NotEmpty
    @NotBlank
    @Column(name = "description")
    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
