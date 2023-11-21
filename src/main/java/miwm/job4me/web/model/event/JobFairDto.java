package miwm.job4me.web.model.event;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class JobFairDto {
    @Schema(description = "Id of the job fair", example = "1")
    private Long id;

    @Schema(description = "Name of the job fair", example = "Akademickie targi pracy")
    private String name;

    @Schema(description = "Job fair organizer id", example = "1")
    private Long organizerId;

    @Schema(description = "Job fair date and time of start", example = "2021-05-01T10:00:00")
    private LocalDateTime dateStart;

    @Schema(description = "Job fair date and time of end", example = "2021-05-01T14:00:00")
    private LocalDateTime dateEnd;

    @Schema(description = "Job fair address of event", example = "ul. Główna 1, 00-001 Warszawa")
    private String address;

    @Schema(description = "Job fair description", example = "Akademickie targi pracy odbywają się dwa razy do roku. Wielu pracodawców szuka wśród studentów i absolwentów pracowników na staże i praktyki.")
    private String description;

    @Schema(description = "Job fair description displayed as summary of event", example = "Akademickie targi pracy znowu w Warszawie! Branże IT i finanse.")
    private String displayDescription;

    @Schema(description = "Job fair photo advertising event", example = "https://picsum.photos/100/100")
    private String photo;
}
