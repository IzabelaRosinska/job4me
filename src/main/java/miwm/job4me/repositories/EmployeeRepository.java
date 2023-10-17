package miwm.job4me.repositories;

import miwm.job4me.model.users.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(nativeQuery = true, value = "select * from employees where email = ?1")
    Employee selectEmployeeByUsername(String username);

}
