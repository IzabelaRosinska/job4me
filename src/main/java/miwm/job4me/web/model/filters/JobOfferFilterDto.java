package miwm.job4me.web.model.filters;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JobOfferFilterDto {
    @Schema(description = "List of cities to filter offers by", example = "[\"Warszawa\"]")
    private List<String> cities;

    @Schema(description = "List of employment forms to filter offers by", example = "[\"praca zdalna\"]")
    private List<String> employmentFormNames;

    @Schema(description = "List of levels to filter offers by", example = "[\"Junior\", \"Mid\"]")
    private List<String> levelNames;

    @Schema(description = "List of contract types to filter offers by", example = "[\"umowa o pracę\"]")
    private List<String> contractTypeNames;

    @Schema(description = "Salary from to filter offers by", example = "2000")
    private Integer salaryFrom;

    @Schema(description = "Salary to to filter offers by", example = "10000")
    private Integer salaryTo;

    @Schema(description = "List of industries to filter offers by", example = "[\"IT\"]")
    private List<String> industryNames;

    @Schema(description = "Offer name to filter offers by", example = "Java")
    private String offerName;

    @Schema(description = "Order to sort offers by (1 - salaryFrom ASC, 2 - salaryFrom DESC, 3 - offerName ASC, 4 - offerName DESC)", example = "1")
    private String order;
}
