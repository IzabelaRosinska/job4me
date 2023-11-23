package miwm.job4me.services.event;

import miwm.job4me.model.event.JobFair;
import miwm.job4me.services.BaseDtoService;
import miwm.job4me.web.model.event.JobFairDto;
import miwm.job4me.web.model.listDisplay.ListDisplayDto;
import org.springframework.data.domain.Page;

public interface JobFairService extends BaseDtoService<JobFair, JobFairDto, Long> {
    Page<JobFair> findAllByFilters(int page, int size);

    Page<JobFair> findAllOfOrganizerByFilters(int page, int size);

    Page<ListDisplayDto> findAllByFiltersListDisplay(int page, int size);

    Page<ListDisplayDto> findAllOfOrganizerByFiltersListDisplay(int page, int size);

    JobFairDto saveDto(JobFairDto jobFairDto);

    boolean existsById(Long id);

    void strictExistsById(Long id);

    JobFairDto update(Long id, JobFairDto jobFair);

    String getContactEmail(Long id);

    JobFair getJobFairById(Long id);
}
