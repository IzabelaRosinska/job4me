package miwm.job4me.web.model.cv;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class MultiEntrySectionDto {
    protected Long id;
    protected String description;
    protected Long employeeId;
}
