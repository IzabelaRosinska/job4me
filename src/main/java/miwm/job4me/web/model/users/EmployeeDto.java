package miwm.job4me.web.model.users;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class EmployeeDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String telephone;
    private String aboutMe;
    private String interests;
    private ArrayList<String> education;
    private ArrayList<String> experience;
    private ArrayList<String> projects;
    private ArrayList<String> skills;
}
