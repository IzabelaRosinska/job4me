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
    @Query("SELECT j FROM JobOffer j " +
            "WHERE (COALESCE(:offerIds, '') = '' OR j.id IN (:offerIds)) " +
            "AND (:isActive IS NULL OR j.isActive = :isActive)")
    Page<JobOffer> findByIsActive(Pageable pageable,
                                  @Param("isActive") Boolean isActive,
                                  @Param("offerIds") List<Long> offerIds);

    @Query("SELECT DISTINCT j FROM JobOffer j " +
            "LEFT JOIN j.localizations loc " +
            "LEFT JOIN j.employmentForms empForm " +
            "LEFT JOIN j.levels lev " +
            "LEFT JOIN j.contractTypes contractType " +
            "LEFT JOIN j.industries industry " +
            "WHERE (COALESCE(:offerIds, '') = '' OR j.id IN (:offerIds))" +
            "AND (COALESCE(:employerIds, '') = '' OR j.employer.id IN (:employerIds))" +
            "AND (:isActive IS NULL OR j.isActive = :isActive) " +
            "AND (COALESCE(:cities, '') = '' OR loc.city IN (:cities)) " +
            "AND (COALESCE(:employmentFormNames, '') = '' OR empForm.name IN (:employmentFormNames)) " +
            "AND (COALESCE(:levelNames, '') = '' OR lev.name IN (:levelNames)) " +
            "AND (COALESCE(:contractTypeNames, '') = '' OR contractType.name IN (:contractTypeNames)) " +
            "AND (:salaryFrom IS NULL OR j.salaryFrom >= :salaryFrom) " +
            "AND (:salaryTo IS NULL OR j.salaryTo <= :salaryTo) " +
            "AND (COALESCE(:industryNames, '') = '' OR industry.name IN (:industryNames)) " +
            "AND (:offerName IS NULL OR LOWER(j.offerName) LIKE LOWER(CONCAT('%', :offerName, '%')))")
    Page<JobOffer> findAllOffersByFilters(Pageable pageable,
                                          @Param("employerIds") List<Long> employerIds,
                                          @Param("isActive") Boolean isActive,
                                          @Param("cities") List<String> cities,
                                          @Param("employmentFormNames") List<String> employmentFormNames,
                                          @Param("levelNames") List<String> levelNames,
                                          @Param("contractTypeNames") List<String> contractTypeNames,
                                          @Param("salaryFrom") Integer salaryFrom,
                                          @Param("salaryTo") Integer salaryTo,
                                          @Param("industryNames") List<String> industryNames,
                                          @Param("offerName") String offerName,
                                          @Param("offerIds") List<Long> offerIds);

    @Query("SELECT DISTINCT j FROM JobOffer j " +
            "WHERE COALESCE(:offerIds, '') = '' OR j.id IN (:offerIds)" +
            "AND COALESCE(:employerIds, '') = '' OR j.employer.id IN (:employerIds)" +
            "AND (:isActive IS NULL OR j.isActive = :isActive)")
    Page<JobOffer> findAllOffersOfEmployers(Pageable pageable,
                                            @Param("employerIds") List<Long> employerIds,
                                            @Param("isActive") Boolean isActive,
                                            @Param("offerIds") List<Long> offerIds);

    @Query("SELECT DISTINCT j FROM JobOffer j " +
            "LEFT JOIN j.localizations loc " +
            "LEFT JOIN j.employmentForms empForm " +
            "LEFT JOIN j.levels lev " +
            "LEFT JOIN j.contractTypes contractType " +
            "LEFT JOIN j.industries industry " +
            "WHERE (COALESCE(:offerIds, '') = '' OR j.id IN (:offerIds))" +
            "AND (COALESCE(:employerIds, '') = '' OR j.employer.id IN (:employerIds))" +
            "AND (j.isActive = True) " +
            "AND (COALESCE(:cities, '') = '' OR loc.city IN (:cities)) " +
            "AND (COALESCE(:employmentFormNames, '') = '' OR empForm.name IN (:employmentFormNames)) " +
            "AND (COALESCE(:levelNames, '') = '' OR lev.name IN (:levelNames)) " +
            "AND (COALESCE(:contractTypeNames, '') = '' OR contractType.name IN (:contractTypeNames)) " +
            "AND (:salaryFrom IS NULL OR j.salaryFrom >= :salaryFrom) " +
            "AND (:salaryTo IS NULL OR j.salaryTo <= :salaryTo) " +
            "AND (COALESCE(:industryNames, '') = '' OR industry.name IN (:industryNames)) " +
            "AND (:offerName IS NULL OR LOWER(j.offerName) LIKE LOWER(CONCAT('%', :offerName, '%')))" +
            "ORDER BY FUNCTION('FIELD', j.id, :offerIds)")
    Page<JobOffer> findAllRecommendedOffersByFilters(Pageable pageable,
                                                     @Param("employerIds") List<Long> employerIds,
                                                     @Param("cities") List<String> cities,
                                                     @Param("employmentFormNames") List<String> employmentFormNames,
                                                     @Param("levelNames") List<String> levelNames,
                                                     @Param("contractTypeNames") List<String> contractTypeNames,
                                                     @Param("salaryFrom") Integer salaryFrom,
                                                     @Param("salaryTo") Integer salaryTo,
                                                     @Param("industryNames") List<String> industryNames,
                                                     @Param("offerName") String offerName,
                                                     @Param("offerIds") List<Long> offerIds);

    @Query("SELECT j FROM JobOffer j " +
            "WHERE (COALESCE(:offerIds, '') = '' OR j.id IN (:offerIds)) " +
            "AND (j.isActive = TRUE)" +
            "ORDER BY FUNCTION('FIELD', j.id, :offerIds)")
    Page<JobOffer> findAllRecommendedOffers(Pageable pageable, @Param("offerIds") List<Long> offerIds);
}
