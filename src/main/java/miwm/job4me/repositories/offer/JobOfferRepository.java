package miwm.job4me.repositories.offer;

import miwm.job4me.model.offer.JobOffer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {
    @Query("SELECT j FROM JobOffer j " +
            "JOIN j.localizations loc " +
            "JOIN j.employmentForms empForm " +
            "JOIN j.levels level " +
            "JOIN j.contractTypes contractType " +
            "JOIN j.industries industry " +
            "WHERE (:city IS NULL OR loc.city = :city) " +
            "AND (:employmentFormName IS NULL OR empForm.name = :employmentFormName) " +
            "AND (:levelName IS NULL OR level.name = :levelName) " +
            "AND (:contractTypeName IS NULL OR contractType.name = :contractTypeName) " +
            "AND (:salaryFrom IS NULL OR j.salaryFrom >= :salaryFrom) " +
            "AND (:salaryTo IS NULL OR j.salaryTo <= :salaryTo) " +
            "AND (:industryName IS NULL OR industry.name = :industryName) " +
            "AND (:offerName IS NULL OR LOWER(j.offerName) LIKE LOWER(:offerName))")
    Page<JobOffer> findByFilters(Pageable pageable,
                                 @Param("city") String city,
                                 @Param("employmentFormName") String employmentFormName,
                                 @Param("levelName") String levelName,
                                 @Param("contractTypeName") String contractTypeName,
                                 @Param("salaryFrom") Integer salaryFrom,
                                 @Param("salaryTo") Integer salaryTo,
                                 @Param("industryName") String industryName,
                                 @Param("offerName") String offerName);

    @Query("SELECT j FROM JobOffer j " +
            "JOIN j.localizations loc " +
            "JOIN j.employmentForms empForm " +
            "JOIN j.levels level " +
            "JOIN j.contractTypes contractType " +
            "JOIN j.industries industry " +
            "WHERE (:employerId IS NULL OR j.employer.id = :employerId) " +
            "AND (:city IS NULL OR loc.city = :city) " +
            "AND (:employmentFormName IS NULL OR empForm.name = :employmentFormName) " +
            "AND (:levelName IS NULL OR level.name = :levelName) " +
            "AND (:contractTypeName IS NULL OR contractType.name = :contractTypeName) " +
            "AND (:salaryFrom IS NULL OR j.salaryFrom >= :salaryFrom) " +
            "AND (:salaryTo IS NULL OR j.salaryTo <= :salaryTo) " +
            "AND (:industryName IS NULL OR industry.name = :industryName) " +
            "AND (:offerName IS NULL OR LOWER(j.offerName) LIKE LOWER(:offerName))")
    Page<JobOffer> findByFiltersForEmployer(Pageable pageable,
                                            @Param("employerId") Long employerId,
                                            @Param("city") String city,
                                            @Param("employmentFormName") String employmentFormName,
                                            @Param("levelName") String levelName,
                                            @Param("contractTypeName") String contractTypeName,
                                            @Param("salaryFrom") Integer salaryFrom,
                                            @Param("salaryTo") Integer salaryTo,
                                            @Param("industryName") String industryName,
                                            @Param("offerName") String offerName);

}
