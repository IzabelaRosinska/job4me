package miwm.job4me.web.model.offer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmploymentFormDto {
    @Schema(description = "Employment form id", example = "1")
    private Long id;

    @Schema(description = "Employment form name", example = "Remote")
    private String name;
}
