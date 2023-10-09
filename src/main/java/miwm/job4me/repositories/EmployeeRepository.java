package miwm.job4me.repositories;

import miwm.job4me.model.users.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    @Query(nativeQuery = true, value = "select * from employees where email = ?1")
    Employee selectEmployeeByUsername(String username);

}
