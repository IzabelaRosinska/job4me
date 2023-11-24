package miwm.job4me.services.event;

import miwm.job4me.model.event.JobFairEmployerParticipation;
import miwm.job4me.services.users.EmployeeService;
import miwm.job4me.services.users.SavedEmployerService;
import miwm.job4me.web.mappers.listDisplay.ListDisplayMapper;
import miwm.job4me.web.mappers.listDisplay.ListDisplaySavedMapper;
import miwm.job4me.web.model.listDisplay.ListDisplayDto;
import miwm.job4me.web.model.listDisplay.ListDisplaySavedDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class JobFairEmployerServiceImpl implements JobFairEmployerService {
    private final JobFairEmployerParticipationService jobFairEmployerParticipationService;
    private final ListDisplayMapper listDisplayMapper;
    private final ListDisplaySavedMapper listDisplaySavedMapper;
    private final SavedEmployerService savedEmployerService;
    private final EmployeeService employeeService;

    public JobFairEmployerServiceImpl(JobFairEmployerParticipationService jobFairEmployerParticipationService, ListDisplayMapper listDisplayMapper, ListDisplaySavedMapper listDisplaySavedMapper, SavedEmployerService savedEmployerService, EmployeeService employeeService) {
        this.jobFairEmployerParticipationService = jobFairEmployerParticipationService;
        this.listDisplayMapper = listDisplayMapper;
        this.listDisplaySavedMapper = listDisplaySavedMapper;
        this.savedEmployerService = savedEmployerService;
        this.employeeService = employeeService;
    }

    @Override
    public Page<ListDisplaySavedDto> findAllEmployersForJobFairEmployeeView(int page, int size, Long jobFairId, String employerCompanyName) {
        return jobFairEmployerParticipationService.findAllByFilters(page, size, true, jobFairId, null, "", employerCompanyName)
                .map(JobFairEmployerParticipation::getEmployer)
                .map(listDisplaySavedMapper::toDtoFromEmployer)
                .map(listDisplaySavedDto -> {
                    listDisplaySavedDto.setIsSaved(savedEmployerService.checkIfSaved(listDisplaySavedDto.getId()));
                    return listDisplaySavedDto;
                });
    }

    @Override
    public Page<ListDisplayDto> findAllEmployersForJobFairGeneralView(int page, int size, Long jobFairId, String employerCompanyName) {
        return jobFairEmployerParticipationService.findAllByFilters(page, size, true, jobFairId, null, "", employerCompanyName)
                .map(JobFairEmployerParticipation::getEmployer)
                .map(listDisplayMapper::toDtoFromEmployer);
    }
}
