package miwm.job4me.model.users;

import lombok.Getter;
import lombok.Setter;
import miwm.job4me.validators.PasswordMatches;
import miwm.job4me.validators.ValidEmail;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@PasswordMatches
public class RegisterData {

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
