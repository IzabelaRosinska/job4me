package miwm.job4me.repositories.users;

import miwm.job4me.model.users.Employee;
import miwm.job4me.model.users.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrganizerRepository extends JpaRepository<Organizer, Long> {

    @Query(nativeQuery = true, value = "select * from organizers where email = ?1")
    Organizer selectOrganizerByUsername(String username);
}
