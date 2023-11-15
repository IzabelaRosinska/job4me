package miwm.job4me.web.model.users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployerDto {

    private String id;
    private String companyName;
    private String description;
    private String displayDescription;
    private String telephone;
    private String email;
    private String photo;
    private String address;
}
