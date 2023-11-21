package miwm.job4me.repositories.offer;

import miwm.job4me.model.offer.JobOffer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {
//    @Query("SELECT DISTINCT j FROM JobOffer j " +
//            "LEFT JOIN j.localizations loc " +
//            "LEFT JOIN j.employmentForms empForm " +
//            "LEFT JOIN j.levels level " +
//            "LEFT JOIN j.contractTypes contractType " +
//            "LEFT JOIN j.industries industry " +
//            "WHERE :employerId IS NULL OR j.employer.id IN (:employerId) " +
//            "AND (:isActive IS NULL OR j.isActive = :isActive) " +
//            "AND (:cities IS NULL OR loc.city IN (:cities)) " +
//            "AND (:employmentFormNames IS NULL OR empForm.name IN (:employmentFormNames)) " +
//            "AND (:levelNames IS NULL OR level.name IN (:levelNames)) " +
//            "AND (:contractTypeNames IS NULL OR contractType.name IN (:contractTypeNames)) " +
//            "AND (:salaryFrom IS NULL OR j.salaryFrom >= :salaryFrom) " +
//            "AND (:salaryTo IS NULL OR j.salaryTo <= :salaryTo) " +
//            "AND (:industryNames IS NULL OR industry.name IN (:industryNames)) " +
//            "AND (:offerName IS NULL OR LOWER(j.offerName) LIKE LOWER(:offerName))")
//    Page<JobOffer> findAllOffersByFilters(Pageable pageable,
//                                          @Param("employerIds") List<Long> employerIds,
//                                          @Param("isActive") Boolean isActive,
//                                          @Param("cities") List<String> cities,
//                                          @Param("employmentFormNames") List<String> employmentFormNames,
//                                          @Param("levelNames") List<String> levelNames,
//                                          @Param("contractTypeNames") List<String> contractTypeNames,
//                                          @Param("salaryFrom") Integer salaryFrom,
//                                          @Param("salaryTo") Integer salaryTo,
//                                          @Param("industryNames") List<String> industryNames,
//                                          @Param("offerName") String offerName);

    @Query(value = "SELECT DISTINCT j.* FROM " +
            "(SELECT DISTINCT j.* FROM job_offers j " +
            "WHERE (?1 IS NULL OR j.employer_id IN ?1) " +
            "AND (?2 IS NULL OR j.is_active = ?2) " +
            "AND (?3 IS NULL OR j.salary_from >= ?3) " +
            "AND (:salaryTo IS NULL OR j.salary_to <= :salaryTo) " +
            "AND (:offerName IS NULL OR LOWER(j.offer_name) LIKE LOWER(CONCAT('%', :offerName, '%')))) j " +
            "LEFT JOIN (SELECT loc.* FROM job_offer_localizations loc " +
            "LEFT JOIN localizations l ON loc.localization_id = l.id" +
            "WHERE (?5 IS NULL OR l.city IN ?5)) loc ON j.id = loc.job_offer_id ", nativeQuery = true)
    Page<JobOffer> findAllOffersByFilters(Pageable pageable,
                                          List<Long> employerIds,
                                          Boolean isActive,
                                          Integer salaryFrom,
                                          @Param("salaryTo") Integer salaryTo,
                                          @Param("offerName") String offerName);
}
