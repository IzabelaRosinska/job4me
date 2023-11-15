package miwm.job4me.web.model.users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import miwm.job4me.validators.PasswordMatches;
import miwm.job4me.validators.ValidEmail;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@PasswordMatches
@NoArgsConstructor
public class UserDto {

    @Builder
    public UserDto(String username, String role, String password) {
        this.username = username;
        this.role = role;
        this.password = password;
    }

    @ValidEmail
    @NotNull
    @NotEmpty
    @Schema(description = "Email of the user used as username", example = "john.smith@gmail.com")
    private String username;

    @NotNull
    @NotEmpty
    @Schema(description = "Role of the user", example = "ROLE_EMPLOYEE")
    private String role;

    @NotNull
    @NotEmpty
    @Length(min = 5, max = 15)
    @Schema(description = "Password of the user", example = "password")
    private String password;

    @Schema(description = "Repeated password of the user for the confirmation", example = "password")
    private String matchingPassword;

}
