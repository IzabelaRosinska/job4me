package miwm.job4me.web.model.offer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LevelDto {
    @Schema(description = "Level id", example = "1")
    private Long id;

    @Schema(description = "Level name", example = "Junior")
    private String name;
}
