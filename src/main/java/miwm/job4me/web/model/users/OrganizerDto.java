package miwm.job4me.web.model.users;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class OrganizerDto {

    private String id;
    private String name;
    private String description;
    private String telephone;
    private String contactEmail;
    private ArrayList<JobFairDto> fairs;

}
