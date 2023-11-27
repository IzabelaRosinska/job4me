package miwm.job4me.model.users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinkedinCheckout {

    @Schema(description = "Url to redirect to LinkedIn", example = "https://www.linkedin.com/oauth/v2")
    private String url;
}
