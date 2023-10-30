package miwm.job4me.model.users;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "employers")
public class Employer extends Person {

    @Builder
    public Employer(Long id, String telephone, String email, String password, SimpleGrantedAuthority userRole,
                    String companyName, String description, String displayDescription, String photo, String address) {
        super(id, telephone, email, password, userRole);
        this.companyName = companyName;
        this.description = description;
        this.displayDescription = displayDescription;
        this.photo = photo;
        this.address = address;
    }

    @Column(name = "company_name", length = 100)
    private String companyName;

    @Lob
    @Column(name = "description", length = 500)
    private String description;

    @Lob
    @Column(name = "display_description", length = 1000)
    private String displayDescription;

    @Column(name = "photo")
    private String photo;

    @Column(name = "address", length = 100)
    private String address;

    public String toString(){
        return getUsername();
    }
}
