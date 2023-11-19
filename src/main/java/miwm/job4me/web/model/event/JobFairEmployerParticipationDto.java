package miwm.job4me.web.model.event;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobFairEmployerParticipationDto {
    @Schema(description = "Id of the job fair employer participation", example = "1")
    private Long id;

    @Schema(description = "Id of the job fair", example = "1")
    private Long jobFairId;

    @Schema(description = "Name of the job fair", example = "Akademickie targi pracy")
    private String jobFairName;

    @Schema(description = "Id of the employer", example = "1")
    private Long employerId;

    @Schema(description = "Name of the employer", example = "Google")
    private String employerCompanyName;

    @Schema(description = "Is employer request to participate in job fair accepted", example = "true")
    private Boolean isAccepted;
}
