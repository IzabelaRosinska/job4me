package miwm.job4me.repositories.users;

import miwm.job4me.model.users.SavedEmployer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SavedEmployerRepository extends JpaRepository<SavedEmployer, Long> {

    @Query(nativeQuery = true, value = "select * from saved_employers where employee_id = ?1 AND employer_id = ?2")
    Optional<SavedEmployer> findByIds(Long employeeId, Long employerId);
}
