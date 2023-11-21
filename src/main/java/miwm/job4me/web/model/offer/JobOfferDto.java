package miwm.job4me.web.model.offer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class JobOfferDto {
    @Schema(description = "Job offer id", example = "1")
    private Long id;

    @Schema(description = "Job offer name", example = "Java Developer - Remote web developer")
    private String offerName;

    @Schema(description = "Job offer employer id", example = "1")
    private Long employerId;

    @Schema(description = "Job offer industries", example = "[\"IT\", \"Sprzedaż\"]")
    private ArrayList<String> industries;

    @Schema(description = "Job offer localizations", example = "[\"Warszawa\", \"Wrocław\"]")
    private ArrayList<String> localizations;

    @Schema(description = "Job offer employment forms", example = "[\"praca zdalna\"]")
    private ArrayList<String> employmentForms;

    @Schema(description = "Job offer salary from", example = "3000")
    private Integer salaryFrom;

    @Schema(description = "Job offer salary to", example = "5000")
    private Integer salaryTo;

    @Schema(description = "Job offer contract types", example = "[\"B2B\"]")
    private ArrayList<String> contractTypes;

    @Schema(description = "Job offer working time", example = "8h")
    private String workingTime;

    @Schema(description = "Job offer levels", example = "[\"Junior\", \"Mid\"]")
    private ArrayList<String> levels;

    @Schema(description = "Job offer requirements", example = "[\"Java\", \"Spring\"]")
    private ArrayList<String> requirements;

    @Schema(description = "Job offer extra skills", example = "[\"English\", \"Driving license\"]")
    private ArrayList<String> extraSkills;

    @Schema(description = "Job offer duties", example = "Java Developer - Remote web developer")
    private String duties;

    @Schema(description = "Job offer description", example = "Java Developer - Remote web developer")
    private String description;

    @Schema(description = "True if job offer is active (offers can be inactive and not deleted)", example = "true")
    private Boolean isActive;

}
