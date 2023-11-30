package miwm.job4me.repositories.users;

import miwm.job4me.model.users.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface EmployerRepository extends JpaRepository<Employer, Long> {

    @Query(nativeQuery = true, value = "select * from employers where email = ?1")
    Employer selectEmployerByUsername(String username);

    @Query(nativeQuery = true, value = "select * from password_reset_token p INNER JOIN employers e ON p.employer_id=e.id where p.token LIKE ?1")
    Optional<Employer> getEmployerByToken(String token);
}