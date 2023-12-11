package miwm.job4me.repositories.users;

import miwm.job4me.model.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    @Query(nativeQuery = true, value = "select * from admins where email = ?1")
    Admin selectAdminByUsername(String username);
}
