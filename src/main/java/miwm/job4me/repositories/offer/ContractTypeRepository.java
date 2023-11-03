package miwm.job4me.repositories.offer;

import miwm.job4me.model.offer.ContractType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractTypeRepository extends JpaRepository<ContractType, Long> {
    Page<ContractType> findAll(Pageable pageable);
}
