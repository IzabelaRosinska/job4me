package miwm.job4me.web.model.users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PasswordDto {

    @NotNull
    @NotEmpty
    @Length(min = 5, max = 15)
    @Schema(description = "User password to change", example = "password")
    private String password;

    @NotNull
    @NotEmpty
    @Length(min = 5, max = 15)
    @Schema(description = "New user password", example = "password")
    private String matchingPassword;
}
