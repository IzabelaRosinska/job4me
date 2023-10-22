package miwm.job4me.repositories.users;

import miwm.job4me.model.users.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployerRepository extends JpaRepository<Employer, Long> {
}