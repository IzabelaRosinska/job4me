package miwm.job4me.web.model.users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizerDto {

    @Schema(description = "Id of the organizer", example = "1")
    private Long id;

    @Schema(description = "Name of the organizer", example = "ABC")
    private String organizerName;

    @Schema(description = "Description of the organizer", example = "The biggest organizer in industry")
    private String description;

    @Schema(description = "Contact telephone of the organizer", example = "12345678")
    private String telephone;

    @Schema(description = "Contact email of the organizer", example = "abc@gmail.com")
    private String email;
}
