package miwm.job4me.repositories.event;

import miwm.job4me.model.event.JobFair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobFairRepository extends JpaRepository<JobFair, Long> {
    Page<JobFair> findAllByFilters(Pageable pageable);
}
