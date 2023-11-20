package miwm.job4me.services.event;

import miwm.job4me.web.model.listDisplay.ListDisplayDto;
import miwm.job4me.web.model.listDisplay.ListDisplaySavedDto;
import org.springframework.data.domain.Page;

public interface JobFairEmployerService {
    Page<ListDisplaySavedDto> findAllEmployersForJobFairEmployeeView(int page, int size, Long jobFairId, String employerCompanyName);

    Page<ListDisplayDto> findAllEmployersForJobFairOrganizerView(int page, int size, Long jobFairId, String employerCompanyName);

}
