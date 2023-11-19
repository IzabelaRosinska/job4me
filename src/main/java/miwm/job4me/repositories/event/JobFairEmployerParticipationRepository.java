package miwm.job4me.repositories.event;

import miwm.job4me.model.event.JobFairEmployerParticipation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobFairEmployerParticipationRepository extends JpaRepository<JobFairEmployerParticipation, Long> {
    @Query("SELECT DISTINCT j FROM JobFairEmployerParticipation j " +
            "WHERE (:isAccepted IS NULL OR j.isAccepted = :isAccepted)")
    Page<JobFairEmployerParticipation> findAllByIsAccepted(Pageable pageable, @Param("isAccepted") Boolean isAccepted);

    boolean existsByJobFair_IdAndEmployer_Id(Long id, Long id1);

    @Query("SELECT DISTINCT j FROM JobFairEmployerParticipation j " +
            "WHERE (:employerId IS NULL OR j.employer.id = :employerId) " +
            "AND (:isAccepted IS NULL OR j.isAccepted = :isAccepted)")
    Page<JobFairEmployerParticipation> findAllByEmployerIdAndIsAccepted(Pageable pageable, @Param("employerId") Long employerId, @Param("isAccepted") Boolean isAccepted);

    @Query("SELECT DISTINCT j FROM JobFairEmployerParticipation j " +
            "WHERE (:jobFairId IS NULL OR j.jobFair.id = :jobFairId) " +
            "AND (:isAccepted IS NULL OR j.isAccepted = :isAccepted)")
    Page<JobFairEmployerParticipation> findByJobFair_IdAndJobFair_Organizer_IdAndIsAccepted(Pageable pageable, @Param("jobFairId") Long jobFairId, @Param("isAccepted") Boolean isAccepted);

    @Query("SELECT DISTINCT j FROM JobFairEmployerParticipation j " +
            "WHERE (:organizerId IS NULL OR j.jobFair.organizer.id = :organizerId) " +
            "AND (:isAccepted IS NULL OR j.isAccepted = :isAccepted)")
    Page<JobFairEmployerParticipation> findByJobFair_Organizer_IdAndIsAccepted(Pageable pageable, @Param("organizerId") Long organizerId, @Param("isAccepted") Boolean isAccepted);
}
