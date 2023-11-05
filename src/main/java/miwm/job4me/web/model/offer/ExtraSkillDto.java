package miwm.job4me.web.model.offer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExtraSkillDto {
    @Schema(description = "Extra skill id", example = "1")
    private Long id;

    @Schema(description = "Extra skill description", example = "Driving license")
    private String description;
}
