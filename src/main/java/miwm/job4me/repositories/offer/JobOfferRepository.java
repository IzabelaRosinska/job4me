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
            "LEFT JOIN j.localizations loc " +
            "LEFT JOIN j.employmentForms empForm " +
            "LEFT JOIN j.levels level " +
            "LEFT JOIN j.contractTypes contractType " +
            "LEFT JOIN j.industries industry " +
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

//    @Query("SELECT DISTINCT j FROM JobOffer j " +
//            "JOIN j.localizations loc " +
//            "JOIN j.employmentForms empForm " +
//            "JOIN j.levels level " +
//            "JOIN j.contractTypes contractType " +
//            "JOIN j.industries industry " +
//            "WHERE (:cities IS NULL OR loc.city IN :cities) " +
//            "AND (:employmentFormNames IS NULL OR empForm.name IN :employmentFormNames) " +
//            "AND (:levelNames IS NULL OR level.name IN :levelNames) " +
//            "AND (:contractTypeNames IS NULL OR contractType.name IN :contractTypeNames) " +
//            "AND (:salaryFrom IS NULL OR j.salaryFrom >= :salaryFrom) " +
//            "AND (:salaryTo IS NULL OR j.salaryTo <= :salaryTo) " +
//            "AND (:industryNames IS NULL OR industry.name IN :industryNames) " +
//            "AND (:offerName IS NULL OR LOWER(j.offerName) LIKE LOWER(:offerName))" +
//            "ORDER BY " + ":order")
//    Page<JobOffer> findByFilters(Pageable pageable,
//                                 @Param("cities") String[] cities,
//                                 @Param("employmentFormNames") String[] employmentFormNames,
//                                 @Param("levelNames") String[] levelNames,
//                                 @Param("contractTypeNames") String[] contractTypeNames,
//                                 @Param("salaryFrom") Integer salaryFrom,
//                                 @Param("salaryTo") Integer salaryTo,
//                                 @Param("industryNames") String[] industryNames,
//                                 @Param("offerName") String offerName,
//                                 @Param("order") String order);

//    @Query("SELECT DISTINCT j FROM JobOffer j " +
//            "LEFT JOIN j.localizations loc " +
//            "LEFT JOIN j.employmentForms empForm " +
//            "LEFT JOIN j.levels level " +
//            "LEFT JOIN j.contractTypes contractType " +
//            "LEFT JOIN j.industries industry " +
//            "WHERE (:cities IS NULL OR loc.city IN :cities) " +
//            "AND (:employmentFormNames IS NULL OR empForm.name IN :employmentFormNames) " +
//            "AND (:levelNames IS NULL OR level.name IN :levelNames) " +
//            "AND (:contractTypeNames IS NULL OR contractType.name IN :contractTypeNames) " +
//            "AND (:salaryFrom IS NULL OR j.salaryFrom >= :salaryFrom) " +
//            "AND (:salaryTo IS NULL OR j.salaryTo <= :salaryTo) " +
//            "AND (:industryNames IS NULL OR industry.name IN :industryNames) " +
//            "AND (:offerName IS NULL OR LOWER(j.offerName) LIKE LOWER(:offerName))" +
//            "ORDER BY " + ":order")
//    Page<JobOffer> findByFilters(Pageable pageable,
//                                 @Param("cities") String[] cities,
//                                 @Param("employmentFormNames") String[] employmentFormNames,
//                                 @Param("levelNames") String[] levelNames,
//                                 @Param("contractTypeNames") String[] contractTypeNames,
//                                 @Param("salaryFrom") Integer salaryFrom,
//                                 @Param("salaryTo") Integer salaryTo,
//                                 @Param("industryNames") String[] industryNames,
//                                 @Param("offerName") String offerName,
//                                 @Param("order") String order);

//    @Query("SELECT DISTINCT j FROM JobOffer j " +
//            "LEFT JOIN j.localizations loc " +
//            "LEFT JOIN j.employmentForms empForm " +
//            "LEFT JOIN j.levels level " +
//            "LEFT JOIN j.contractTypes contractType " +
//            "LEFT JOIN j.industries industry " +
//            "WHERE (:cities IS NULL OR loc.city IN :cities) " +
//            "AND (:employmentFormNames IS NULL OR empForm.name IN :employmentFormNames) " +
//            "AND (:levelNames IS NULL OR level.name IN :levelNames) " +
//            "AND (:contractTypeNames IS NULL OR contractType.name IN :contractTypeNames) " +
//            "AND (:salaryFrom IS NULL OR j.salaryFrom >= :salaryFrom) " +
//            "AND (:salaryTo IS NULL OR j.salaryTo <= :salaryTo) " +
//            "AND (:industryNames IS NULL OR industry.name IN :industryNames) " +
//            "AND (:offerName IS NULL OR LOWER(j.offerName) LIKE LOWER(:offerName))" +
//            "ORDER BY " + ":order")
//    Page<JobOffer> findByFilters(Pageable pageable,
//                                 @Param("cities") String[] cities,
//                                 @Param("employmentFormNames") String[] employmentFormNames,
//                                 @Param("levelNames") String[] levelNames,
//                                 @Param("contractTypeNames") String[] contractTypeNames,
//                                 @Param("salaryFrom") Integer salaryFrom,
//                                 @Param("salaryTo") Integer salaryTo,
//                                 @Param("industryNames") String[] industryNames,
//                                 @Param("offerName") String offerName,
//                                 @Param("order") String order);

    @Query("SELECT DISTINCT new miwm.job4me.model.offer.JobOffer(j.id, j.offerName, j.employer, j.salaryFrom, j.salaryTo, j.workingTime, j.localizations, j.levels, j.employmentForms) FROM JobOffer j " +
            "JOIN j.localizations loc " +
            "JOIN j.employmentForms empForm " +
            "JOIN j.levels level " +
            "JOIN j.contractTypes contractType " +
            "JOIN j.industries industry " +
            "WHERE (:cities IS NULL OR loc.city IN :cities) " +
            "AND (:employmentFormNames IS NULL OR empForm.name IN :employmentFormNames) " +
            "AND (:levelNames IS NULL OR level.name IN :levelNames) " +
            "AND (:contractTypeNames IS NULL OR contractType.name IN :contractTypeNames) " +
            "AND (:salaryFrom IS NULL OR j.salaryFrom >= :salaryFrom) " +
            "AND (:salaryTo IS NULL OR j.salaryTo <= :salaryTo) " +
            "AND (:industryNames IS NULL OR industry.name IN :industryNames) " +
            "AND (:offerName IS NULL OR LOWER(j.offerName) LIKE LOWER(:offerName))" +
            "ORDER BY " + ":order")
    Page<JobOffer> findByFilters2(Pageable pageable,
                                  @Param("cities") List<String> cities,
                                  @Param("employmentFormNames") List<String> employmentFormNames,
                                  @Param("levelNames") List<String> levelNames,
                                  @Param("contractTypeNames") List<String> contractTypeNames,
                                  @Param("salaryFrom") Integer salaryFrom,
                                  @Param("salaryTo") Integer salaryTo,
                                  @Param("industryNames") List<String> industryNames,
                                  @Param("offerName") String offerName,
                                  @Param("order") String order);
}
