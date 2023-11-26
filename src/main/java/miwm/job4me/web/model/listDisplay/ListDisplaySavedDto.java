package miwm.job4me.web.model.listDisplay;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListDisplaySavedDto extends ListDisplayDto {

    @Schema(description = "Information if element is saved to liked", example = "true")
    private Boolean isSaved;

}
