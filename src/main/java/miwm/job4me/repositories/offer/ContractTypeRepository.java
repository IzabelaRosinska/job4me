package miwm.job4me.repositories.offer;

import miwm.job4me.model.offer.ContractType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractTypeRepository extends JpaRepository<ContractType, Long> {
    @Query("SELECT c FROM ContractType c WHERE :name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<ContractType> findByNameContaining(Pageable pageable, @Param("name") String name);

    ContractType findByName(String name);

    boolean existsByName(String name);

}
