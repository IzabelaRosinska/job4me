package miwm.job4me.model.token;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import miwm.job4me.model.BaseEntity;
import miwm.job4me.model.users.Employee;
import miwm.job4me.model.users.Employer;
import miwm.job4me.model.users.Person;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class VerificationToken extends BaseEntity {

    private static final int EXPIRATION = 60 * 24;
    private String token;

    @Builder
    public VerificationToken(Long id, String token, Person person) {
        super(id);
        this.token = token;
        if(person.getUserRole().getAuthority().equals("ROLE_EMPLOYEE")) {
            this.employee = (Employee) person;
        }
        else if(person.getUserRole().getAuthority().equals("ROLE_EMPLOYER")) {
            this.employer = (Employer) person;
        }
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    @OneToOne(targetEntity = Employee.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToOne(targetEntity = Employer.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "employer_id")
    private Employer employer;


    private Date expiryDate;

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
}
