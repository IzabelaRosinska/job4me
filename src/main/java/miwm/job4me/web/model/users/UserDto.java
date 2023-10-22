package miwm.job4me.web.model.users;

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
    public UserDto(String email, String role, String password) {
        this.email = email;
        this.role = role;
        this.password = password;
    }

    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    private String role;

    @NotNull
    @NotEmpty
    @Length(min = 5, max = 15)
    private String password;
    private String matchingPassword;

}
