package miwm.job4me.repositories.users;

import miwm.job4me.model.users.Employee;
import miwm.job4me.model.users.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployerRepository extends JpaRepository<Employer, Long> {

    @Query(nativeQuery = true, value = "select * from employers where email = ?1")
    Employer selectEmployerByUsername(String username);
}