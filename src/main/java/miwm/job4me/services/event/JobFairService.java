package miwm.job4me.services.event;

import miwm.job4me.model.event.JobFair;
import miwm.job4me.model.payment.Payment;
import miwm.job4me.services.BaseDtoService;
import miwm.job4me.web.model.event.JobFairDto;
import miwm.job4me.web.model.listDisplay.ListDisplayDto;
import miwm.job4me.web.model.payment.PaymentCheckout;
import org.springframework.data.domain.Page;

public interface JobFairService extends BaseDtoService<JobFair, JobFairDto, Long> {

    Page<JobFair> findAllByFilters(int page, int size, String order, Boolean showUpcoming, String address);

    Page<JobFair> findAllOfOrganizerByFilters(int page, int size, String order, Boolean showUpcoming, String address, Long organizerId, Boolean isPaid);

    Page<ListDisplayDto> findAllByFiltersListDisplay(int page, int size, String order, Boolean showUpcoming, String address);

    Page<ListDisplayDto> findAllOfOrganizerByFiltersListDisplay(int page, int size, String order, Boolean showUpcoming, String address, Long organizerId, Boolean isPaid);

    Page<ListDisplayDto> findAllOfSignedInOrganizerByFiltersListDisplay(int page, int size, String order, Boolean showUpcoming, String address, Boolean isPaid);

    JobFairDto saveDto(JobFairDto jobFairDto, Payment payment);

    boolean existsById(Long id);

    void strictExistsById(Long id);

    JobFairDto update(Long id, JobFairDto jobFair);

    String getContactEmail(Long id);

    JobFair getJobFairById(Long id);

    JobFair getOnlyPaidJobFairById(Long id);

    PaymentCheckout coordinateJobFairPayment(JobFairDto jobFairDto);

    boolean isJobFairCreatedByOrganizer(Long jobFairId);

}
