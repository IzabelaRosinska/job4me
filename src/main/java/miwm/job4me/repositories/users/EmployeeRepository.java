package miwm.job4me.repositories.users;

import miwm.job4me.model.users.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(nativeQuery = true, value = "select * from employees where email = ?1")
    Employee selectEmployeeByUsername(String username);

    @Query(nativeQuery = true, value = "select employee from password_reset_token p INNER JOIN employee e ON p.employee_id=e.id where e.email = ?1")
    Optional<Employee> getEmployeeByToken(String token);
}
