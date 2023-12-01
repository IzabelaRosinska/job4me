package miwm.job4me.web.model.pdf;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PdfDto {
    @Schema(description = "Pdf file in base64 format", example = "xxxxxxxxxxxxxxxxxxxxxxxxxxx")
    private byte[] serializedPdf;

}
