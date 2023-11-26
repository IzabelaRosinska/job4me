package miwm.job4me.repositories.event;

import miwm.job4me.model.event.JobFairEmployerParticipation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface JobFairEmployerParticipationRepository extends JpaRepository<JobFairEmployerParticipation, Long> {
    Set<JobFairEmployerParticipation> findByJobFair_IdAndIsAccepted(Long jobFairId, Boolean isAccepted);

    @Query("SELECT DISTINCT j FROM JobFairEmployerParticipation j " +
            "WHERE (:isAccepted IS NULL OR j.isAccepted = :isAccepted)" +
            "AND (:organizerId IS NULL OR j.jobFair.organizer.id = :organizerId) " +
            "AND (:jobFairId IS NULL OR j.jobFair.id = :jobFairId) " +
            "AND (:employerId IS NULL OR j.employer.id = :employerId) " +
            "AND (:jobFairName IS NULL OR LOWER(j.jobFair.name) LIKE LOWER(CONCAT('%', :jobFairName, '%')))" +
            "AND (:employerCompanyName IS NULL OR LOWER(j.employer.companyName) LIKE LOWER(CONCAT('%', :employerCompanyName, '%')))")
    Page<JobFairEmployerParticipation> findAllByFilters(Pageable pageable,
                                                        @Param("isAccepted") Boolean isAccepted,
                                                        @Param("organizerId") Long organizerId,
                                                        @Param("jobFairId") Long jobFairId,
                                                        @Param("employerId") Long employerId,
                                                        @Param("jobFairName") String jobFairName,
                                                        @Param("employerCompanyName") String employerCompanyName);

    boolean existsByJobFair_IdAndEmployer_Id(Long id, Long id1);

}
