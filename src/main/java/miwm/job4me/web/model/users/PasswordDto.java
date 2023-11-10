package miwm.job4me.web.model.users;

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
    private  String token;

    @NotNull
    @NotEmpty
    private String oldPassword;

    @NotNull
    @NotEmpty
    @Length(min = 5, max = 15)
    private String newPassword;
}
