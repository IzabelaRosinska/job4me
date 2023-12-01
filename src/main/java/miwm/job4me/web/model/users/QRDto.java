package miwm.job4me.web.model.users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QRDto {
    @Schema(description = "QR file in base64 format", example = "xxxxxxxxxxxxxxxxxxxxxxxxxxx")
    private String serializedQR;

}
