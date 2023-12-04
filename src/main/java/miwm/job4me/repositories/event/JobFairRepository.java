package miwm.job4me.repositories.event;

import miwm.job4me.model.event.JobFair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface JobFairRepository extends JpaRepository<JobFair, Long> {

    @Query("SELECT jf FROM JobFair jf" +
            " WHERE ((:organizerId IS NULL OR jf.organizer.id = :organizerId)" +
            " AND (:isPaid IS NULL OR jf.payment.isPaid = :isPaid)" +
            " AND (:dateStart IS NULL OR jf.dateStart >= :dateStart)" +
            " AND (:dateEnd IS NULL OR jf.dateEnd <= :dateEnd)" +
            " AND (:address IS NULL OR LOWER(jf.address) LIKE LOWER(CONCAT('%', :address, '%'))))")
    Page<JobFair> findAllByFilters(Pageable pageable, @Param("dateStart") LocalDateTime dateStart, @Param("dateEnd") LocalDateTime dateEnd, @Param("address") String address, @Param("organizerId") Long organizerId, @Param("isPaid") Boolean isPaid);

}
