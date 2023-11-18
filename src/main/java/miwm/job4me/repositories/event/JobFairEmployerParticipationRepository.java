package miwm.job4me.repositories.event;

import miwm.job4me.model.event.JobFairEmployerParticipation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JobFairEmployerParticipationRepository extends JpaRepository<JobFairEmployerParticipation, Long> {
    @Query("SELECT jfep FROM JobFairEmployerParticipation jfep")
    Page<JobFairEmployerParticipation> findAllByFilters(Pageable pageable);

    boolean existsByJobFair_IdAndEmployer_Id(Long id, Long id1);
}
