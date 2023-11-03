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

    private ArrayList<String> industries;
    private ArrayList<String> localizations;
    private ArrayList<String> employmentForms;
    private Integer salaryFrom;
    private Integer salaryTo;
    private ArrayList<String> contractTypes;
    private String workingTime;
    private ArrayList<String> levels;
    private ArrayList<String> requirements;
    private ArrayList<String> extraSkills;
    private String duties;
    private String description;

}
