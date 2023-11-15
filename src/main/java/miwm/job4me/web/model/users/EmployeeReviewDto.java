package miwm.job4me.web.model.users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;

@Getter
@Setter
public class EmployeeReviewDto {
    @Schema(description = "Id of the employee", example = "1")
    private Long id;

    @Schema(description = "First name of the employee", example = "John")
    private String firstName;

    @Schema(description = "Last name of the employee", example = "Smith")
    private String lastName;

    @Schema(description = "Contact email of the employee", example = "john.smith@gmail.com")
    private String email;

    @Schema(description = "Contact telephone of the employee", example = "123456789")
    private String telephone;

    @Schema(description = "About me section of the employee", example = "I am passionate about Java.")
    private String aboutMe;

    @Schema(description = "Interests section of the employee", example = "I collect post-stamps.")
    private String interests;

    @Schema(description = "Information if employee is saved to liked", example = "true")
    private Boolean isSaved;

    @Schema(description = "List of Education information of the employee", example = "[\"2020-present: Wroclaw University of Science and Technology, applied computer science\"]")
    private ArrayList<String> education;

    @Schema(description = "List of Experience information of the employee", example = "[\"1 year - Junior Java web developer\"]")
    private ArrayList<String> experience;

    @Schema(description = "List of Projects information of the employee", example = "[\"Library Web App - project created in Java\"]")
    private ArrayList<String> projects;

    @Schema(description = "List of Skills information of the employee", example = "[\"Java\", \"Spring\", \"SQL\"]")
    private ArrayList<String> skills;
}
