package miwm.job4me.services.event;

import miwm.job4me.model.event.JobFair;
import miwm.job4me.services.BaseDtoService;
import miwm.job4me.web.model.event.JobFairDto;
import org.springframework.data.domain.Page;

public interface JobFairService extends BaseDtoService<JobFair, JobFairDto, Long> {
    Page<JobFairDto> findAllByFilters(int page, int size);

    JobFairDto saveDto(JobFairDto jobFairDto);

    boolean existsById(Long id);

    void strictExistsById(Long id);

    JobFairDto update(Long id, JobFairDto jobFair);
}
