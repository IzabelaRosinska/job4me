package miwm.job4me.web.model.offer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractTypeDto {
    @Schema(description = "Contract type id", example = "1")
    private Long id;

    @Schema(description = "Contract type name", example = "B2B")
    private String name;
}
