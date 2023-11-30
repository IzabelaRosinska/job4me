package miwm.job4me.repositories.users;

import miwm.job4me.model.users.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface OrganizerRepository extends JpaRepository<Organizer, Long> {

    @Query(nativeQuery = true, value = "select * from organizers where email = ?1")
    Organizer selectOrganizerByUsername(String username);

    @Query(nativeQuery = true, value = "select * from password_reset_token p INNER JOIN organizers o ON p.organizer_id=o.id where p.token = ?1")
    Optional<Organizer> getOrganizerByToken(String token);
}
