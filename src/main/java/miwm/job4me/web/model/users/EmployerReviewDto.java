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

    @Schema(description = "Photo of employer", example = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fmedium.com%2F%40zakariaalibaba315%2Fhow-does-photopea-work-379e6f6bd9b1&psig=AOvVaw0Vb-80-pdWxvWiZNPNYiql&ust=1700162188850000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCKDbu-vbxoIDFQAAAAAdAAAAABAE")
    private String photo;

    @Schema(description = "Address of the employer", example = "Zabraniecka 20, Warszawa")
    private String address;

    @Schema(description = "Information if employer is saved to liked", example = "true")
    private Boolean isSaved;
}
