package miwm.job4me.services.event;

import miwm.job4me.model.event.JobFairEmployerParticipation;
import miwm.job4me.services.BaseDtoService;
import miwm.job4me.web.model.event.JobFairEmployerParticipationDto;
import org.springframework.data.domain.Page;

public interface JobFairEmployerParticipationService extends BaseDtoService<JobFairEmployerParticipation, JobFairEmployerParticipationDto, Long> {
    Page<JobFairEmployerParticipation> findAllByFilters(int page, int size, Boolean status, Long jobFairId, Long employerId, String jobFairName, String employerCompanyName);

    Page<JobFairEmployerParticipationDto> findAllByEmployerAndFilters(int page, int size, Boolean status, String jobFairName);

    Page<JobFairEmployerParticipationDto> findAllByOrganizerAndFilters(int page, int size, Boolean status, String jobFairName, String employerCompanyName);

    Page<JobFairEmployerParticipationDto> findAllByOrganizerAndJobFairAndFilters(int page, int size, Long jobFairId, Boolean status, String employerCompanyName);

    JobFairEmployerParticipationDto saveDto(JobFairEmployerParticipationDto jobFairDto);

    boolean existsById(Long id);

    void strictExistsById(Long id);

    boolean existsByJobFairIdAndEmployerId(Long jobFairId, Long employerId);

    JobFairEmployerParticipationDto update(Long id, JobFairEmployerParticipationDto jobFairEmployerParticipation);

    JobFairEmployerParticipationDto createParticipationRequestByEmployer(Long jobFairId);

    JobFairEmployerParticipationDto acceptParticipationRequestByOrganizer(Long jobFairParticipationId);

    void rejectParticipationRequestByOrganizer(Long jobFairParticipationId);

    void deleteParticipationRequestByOrganizer(Long jobFairParticipationId);

    void deleteParticipationRequestByEmployer(Long jobFairParticipationId);

    JobFairEmployerParticipation getJobFairEmployerParticipationById(Long jobFairParticipationId);

}