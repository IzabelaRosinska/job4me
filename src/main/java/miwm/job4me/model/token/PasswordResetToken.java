package miwm.job4me.model.token;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import miwm.job4me.model.BaseEntity;
import miwm.job4me.model.users.Employee;
import miwm.job4me.model.users.Employer;
import miwm.job4me.model.users.Organizer;
import miwm.job4me.model.users.Person;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PasswordResetToken extends BaseEntity {

    private static final int EXPIRATION = 60 * 24;
    private String token;

    @Builder
    public PasswordResetToken(Long id, String token, Person person) {
        super(id);
        this.token = token;
        if(person.getUserRole().getAuthority().equals("ROLE_EMPLOYEE_ENABLED")) {
            this.employee = (Employee) person;
        } else if(person.getUserRole().getAuthority().equals("ROLE_EMPLOYER_ENABLED")) {
            this.employer = (Employer) person;
        } else if(person.getUserRole().getAuthority().equals("ROLE_ORGANIZER_ENABLED")) {
            this.organizer = (Organizer) person;
        }
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    @OneToOne(targetEntity = Employee.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToOne(targetEntity = Employer.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "employer_id")
    private Employer employer;

    @OneToOne(targetEntity = Employer.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "organizer_id")
    private Organizer organizer;

    private Date expiryDate;

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
}
