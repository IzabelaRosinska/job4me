package miwm.job4me.web.model.offer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IndustryDto {
    @Schema(description = "Industry id", example = "1")
    private Long id;

    @Schema(description = "Industry name", example = "IT")
    private String name;
}
