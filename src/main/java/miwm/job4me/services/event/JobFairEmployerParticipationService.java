package miwm.job4me.services.event;

import miwm.job4me.model.event.JobFairEmployerParticipation;
import miwm.job4me.services.BaseDtoService;
import miwm.job4me.web.model.event.JobFairEmployerParticipationDto;
import miwm.job4me.web.model.listDisplay.ListDisplayDto;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface JobFairEmployerParticipationService extends BaseDtoService<JobFairEmployerParticipation, JobFairEmployerParticipationDto, Long> {
    Set<Long> findAllEmployersIdsForJobFair(Long jobFairId);

    Page<JobFairEmployerParticipation> findAllByFilters(int page, int size, Boolean status, Long jobFairId, Long employerId, String jobFairName, String employerCompanyName);

    Page<ListDisplayDto> listDisplayFindAllByFilters(int page, int size, Boolean status, Long jobFairId, Long employerId, String jobFairName, String employerCompanyName);

    Page<JobFairEmployerParticipation> findAllByEmployerAndFilters(int page, int size, Boolean status, String jobFairName);

    Page<ListDisplayDto> listDisplayFindAllByEmployerAndFilters(int page, int size, Boolean status, String jobFairName);

    Page<JobFairEmployerParticipation> findAllByOrganizerAndFilters(int page, int size, Boolean status, String jobFairName, String employerCompanyName);

    Page<ListDisplayDto> listDisplayFindAllByOrganizerAndFilters(int page, int size, Boolean status, String jobFairName, String employerCompanyName);

    Page<JobFairEmployerParticipation> findAllByOrganizerAndJobFairAndFilters(int page, int size, Long jobFairId, Boolean status, String employerCompanyName);

    Page<ListDisplayDto> listDisplayFindAllByOrganizerAndJobFairAndFilters(int page, int size, Long jobFairId, Boolean status, String employerCompanyName);

    JobFairEmployerParticipationDto saveDto(JobFairEmployerParticipationDto jobFairDto);

    boolean existsById(Long id);

    void strictExistsById(Long id);

    boolean existsByJobFairIdAndEmployerId(Long jobFairId, Long employerId);

    JobFairEmployerParticipationDto findForEmployerByJobFair(Long jobFairId);

    JobFairEmployerParticipationDto update(Long id, JobFairEmployerParticipationDto jobFairEmployerParticipation);

    JobFairEmployerParticipationDto createParticipationRequestByEmployer(Long jobFairId);

    JobFairEmployerParticipationDto acceptParticipationRequestByOrganizer(Long jobFairParticipationId);

    void rejectParticipationRequestByOrganizer(Long jobFairParticipationId);

    void deleteParticipationRequestByOrganizer(Long jobFairParticipationId);

    void deleteParticipationRequestByEmployer(Long jobFairParticipationId);

    JobFairEmployerParticipation getJobFairEmployerParticipationById(Long jobFairParticipationId);

    boolean canEmployerHaveAccessToJobFairEmployerParticipation(Long jobFairEmployerParticipationId);

    boolean canOrganizerHaveAccessToJobFairEmployerParticipation(Long jobFairEmployerParticipationId);

}
