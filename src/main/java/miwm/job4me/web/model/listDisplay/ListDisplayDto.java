package miwm.job4me.web.model.listDisplay;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListDisplayDto {
    @Schema(description = "Id of the element displayed in the list", example = "1")
    protected Long id;

    @Schema(description = "Name of the element displayed in the list", example = "Politechnika Wrocławska")
    protected String name;

    @Schema(description = "Short description of the element displayed in list", example = "Instytut Informatyki")
    protected String displayDescription;

    @Schema(description = "Photo of employer", example = "https://picsum.photos/100/100")
    protected String photo;
}
