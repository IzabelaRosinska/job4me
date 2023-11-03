package miwm.job4me.web.model.offer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequirementDto {
    @Schema(description = "Requirement id", example = "1")
    private Long id;

    @Schema(description = "Requirement description", example = "Java")
    private String description;
}
