package miwm.job4me.web.model.users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployerReviewDto {

    @Schema(description = "Id of the employer", example = "1")
    private Long id;

    @Schema(description = "Name of the employer", example = "P&G")
    private String companyName;

    @Schema(description = "Description of the employer", example = "One of the biggest sellers of household products")
    private String description;

    @Schema(description = "Short description of the employer", example = "Deliver household products")
    private String displayDescription;

    @Schema(description = "Contact phone to employer", example = "123456789")
    private String telephone;

    @Schema(description = "Contact email to employer", example = "p&g@gmail.com")
    private String email;

    @Schema(description = "Photo of employer", example = "https://picsum.photos/100/100")
    private String photo;

    @Schema(description = "Address of the employer", example = "Zabraniecka 20, Warszawa")
    private String address;

    @Schema(description = "Information if employer is saved to liked", example = "true")
    private Boolean isSaved;
}
