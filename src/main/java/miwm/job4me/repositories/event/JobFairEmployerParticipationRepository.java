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
    Set<Long> findAllEmployersIdsByJobFair_Id(Long id);

    @Query("SELECT DISTINCT j FROM JobFairEmployerParticipation j " +
            "WHERE (:isAccepted IS NULL OR j.isAccepted = :isAccepted)" +
            "AND (:jobFairId IS NULL OR j.jobFair.id = :jobFairId) " +
            "AND (:employerId IS NULL OR j.employer.id = :employerId) " +
            "AND (:jobFairName IS NULL OR LOWER(j.jobFair.name) LIKE LOWER(CONCAT('%', :jobFairName, '%')))" +
            "AND (:employerCompanyName IS NULL OR LOWER(j.employer.companyName) LIKE LOWER(CONCAT('%', :employerCompanyName, '%')))")
    Page<JobFairEmployerParticipation> findAllByFilters(Pageable pageable,
                                                        @Param("isAccepted") Boolean isAccepted,
                                                        @Param("jobFairId") Long jobFairId,
                                                        @Param("employerId") Long employerId,
                                                        @Param("jobFairName") String jobFairName,
                                                        @Param("employerCompanyName") String employerCompanyName);

    boolean existsByJobFair_IdAndEmployer_Id(Long id, Long id1);

    @Query("SELECT DISTINCT j FROM JobFairEmployerParticipation j " +
            "WHERE (:employerId IS NULL OR j.employer.id = :employerId) " +
            "AND (:isAccepted IS NULL OR j.isAccepted = :isAccepted)" +
            "AND (:jobFairName IS NULL OR LOWER(j.jobFair.name) LIKE LOWER(CONCAT('%', :jobFairName, '%')))")
    Page<JobFairEmployerParticipation> findAllByEmployerIdAndByFilters(Pageable pageable, @Param("employerId") Long employerId, @Param("isAccepted") Boolean isAccepted, @Param("jobFairName") String jobFairName);

    @Query("SELECT DISTINCT j FROM JobFairEmployerParticipation j " +
            "WHERE (:jobFairId IS NULL OR j.jobFair.id = :jobFairId) " +
            "AND (:isAccepted IS NULL OR j.isAccepted = :isAccepted)" +
            "AND (:employerCompanyName IS NULL OR LOWER(j.employer.companyName) LIKE LOWER(CONCAT('%', :employerCompanyName, '%')))")
    Page<JobFairEmployerParticipation> findByJobFair_IdAndJobFair_Organizer_IdAndByFilters(Pageable pageable, @Param("jobFairId") Long jobFairId, @Param("isAccepted") Boolean isAccepted, @Param("employerCompanyName") String employerCompanyName);

    @Query("SELECT DISTINCT j FROM JobFairEmployerParticipation j " +
            "WHERE (:organizerId IS NULL OR j.jobFair.organizer.id = :organizerId) " +
            "AND (:isAccepted IS NULL OR j.isAccepted = :isAccepted)" +
            "AND (:jobFairName IS NULL OR LOWER(j.jobFair.name) LIKE LOWER(CONCAT('%', :jobFairName, '%')))" +
            "AND (:employerCompanyName IS NULL OR LOWER(j.employer.companyName) LIKE LOWER(CONCAT('%', :employerCompanyName, '%')))")
    Page<JobFairEmployerParticipation> findByJobFair_Organizer_IdAndByFilters(Pageable pageable, @Param("organizerId") Long organizerId, @Param("isAccepted") Boolean isAccepted, @Param("jobFairName") String jobFairName, @Param("employerCompanyName") String employerCompanyName);
}
