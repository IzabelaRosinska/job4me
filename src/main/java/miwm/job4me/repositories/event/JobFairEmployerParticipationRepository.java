package miwm.job4me.repositories.event;

import miwm.job4me.model.event.JobFairEmployerParticipation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface JobFairEmployerParticipationRepository extends JpaRepository<JobFairEmployerParticipation, Long> {

    Page<JobFairEmployerParticipation> findAllByIsAccepted(Pageable pageable, @Nullable Boolean isAccepted);

    boolean existsByJobFair_IdAndEmployer_Id(Long id, Long id1);

    Page<JobFairEmployerParticipation> findAllByEmployerIdAndIsAccepted(Pageable pageable, Long employerId, @Nullable Boolean isAccepted);

    Page<JobFairEmployerParticipation> findByJobFair_IdAndJobFair_Organizer_IdAndIsAccepted(Pageable pageable, Long jobFairId, Long organizerId, @Nullable Boolean isAccepted);

    Page<JobFairEmployerParticipation> findByJobFair_Organizer_IdAndIsAccepted(Pageable pageable, Long organizerId, @Nullable Boolean isAccepted);
}
