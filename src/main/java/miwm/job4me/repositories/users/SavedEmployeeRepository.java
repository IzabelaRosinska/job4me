package miwm.job4me.repositories.users;

import miwm.job4me.model.users.SavedEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface SavedEmployeeRepository extends JpaRepository<SavedEmployee, Long> {

    @Query(nativeQuery = true, value = "select * from saved_employees where employer_id = ?1 AND employee_id = ?2")
    Optional<SavedEmployee> findByIds(Long employerId, Long employeeId);

    @Query(nativeQuery = true, value = "select * from saved_employees where employer_id = ?1")
    List<SavedEmployee> getSavedForEmployer(Long employerId);

}
