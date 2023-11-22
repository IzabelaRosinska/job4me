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
            "WHERE (:isOfferIdsDefined IS FALSE OR j.id IN (:offerIds))" +
            "AND (:isActive IS NULL OR j.isActive = :isActive)")
    Page<JobOffer> findByIsActive(Pageable pageable,
                                  @Param("isActive") Boolean isActive,
                                  @Param("isOfferIdsDefined") Boolean isOfferIdsDefined,
                                  @Param("offerIds") List<Long> offerIds);


    @Query("SELECT DISTINCT j FROM JobOffer j " +
            "LEFT JOIN j.localizations loc " +
            "LEFT JOIN j.employmentForms empForm " +
            "LEFT JOIN j.levels level " +
            "LEFT JOIN j.contractTypes contractType " +
            "LEFT JOIN j.industries industry " +
            "WHERE (:isOfferIdsDefined IS FALSE OR j.id IN (:offerIds))" +
            "AND (:isEmployerIdsDefined IS FALSE OR j.employer.id IN (:employerIds)) " +
            "AND (:isActive IS NULL OR j.isActive = :isActive) " +
            "AND (:isCitiesDefined IS FALSE OR loc.city IN (:cities)) " +
            "AND (:isEmploymentFormNamesDefined IS FALSE OR empForm.name IN (:employmentFormNames)) " +
            "AND (:isLevelNamesDefined IS FALSE OR level.name IN (:levelNames)) " +
            "AND (:isContractTypeNamesDefined IS FALSE OR contractType.name IN (:contractTypeNames)) " +
            "AND (:salaryFrom IS NULL OR j.salaryFrom >= :salaryFrom) " +
            "AND (:salaryTo IS NULL OR j.salaryTo <= :salaryTo) " +
            "AND (:isIndustryNamesDefined IS FALSE OR industry.name IN (:industryNames)) " +
            "AND (:offerName IS NULL OR LOWER(j.offerName) LIKE LOWER(CONCAT('%', :offerName, '%')))")
    Page<JobOffer> findAllOffersByFilters(Pageable pageable,
                                          @Param("isEmployerIdsDefined") Boolean isEmployerIdsDefined,
                                          @Param("employerIds") List<Long> employerIds,
                                          @Param("isActive") Boolean isActive,
                                          @Param("isCitiesDefined") Boolean isCitiesDefined,
                                          @Param("cities") List<String> cities,
                                          @Param("isEmploymentFormNamesDefined") Boolean isEmploymentFormNamesDefined,
                                          @Param("employmentFormNames") List<String> employmentFormNames,
                                          @Param("isLevelNamesDefined") Boolean isLevelNamesDefined,
                                          @Param("levelNames") List<String> levelNames,
                                          @Param("isContractTypeNamesDefined") Boolean isContractTypeNamesDefined,
                                          @Param("contractTypeNames") List<String> contractTypeNames,
                                          @Param("salaryFrom") Integer salaryFrom,
                                          @Param("salaryTo") Integer salaryTo,
                                          @Param("isIndustryNamesDefined") Boolean isIndustryNamesDefined,
                                          @Param("industryNames") List<String> industryNames,
                                          @Param("offerName") String offerName,
                                          @Param("isOfferIdsDefined") Boolean isOfferIdsDefined,
                                          @Param("offerIds") List<Long> offerIds);

    @Query("SELECT DISTINCT j FROM JobOffer j " +
            "WHERE :isOfferIdsDefined IS FALSE OR j.id IN (:offerIds) " +
            "AND :isEmployerIdsDefined IS FALSE OR j.employer.id IN (:employerIds)" +
            "AND (:isActive IS NULL OR j.isActive = :isActive)")
    Page<JobOffer> findAllOffersOfEmployers(Pageable pageable,
                                            @Param("isEmployerIdsDefined") Boolean isEmployerIdsDefined,
                                            @Param("employerIds") List<Long> employerIds,
                                            @Param("isActive") Boolean isActive,
                                            @Param("isOfferIdsDefined") Boolean isOfferIdsDefined,
                                            @Param("offerIds") List<Long> offerIds);

}
