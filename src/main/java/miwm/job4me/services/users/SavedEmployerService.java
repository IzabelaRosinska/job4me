package miwm.job4me.services.users;

import miwm.job4me.model.users.SavedEmployer;
import miwm.job4me.services.BaseService;
import miwm.job4me.web.model.users.EmployerReviewDto;

import java.util.List;
import java.util.Optional;

public interface SavedEmployerService extends BaseService<SavedEmployer, Long> {

    List<SavedEmployer> getSavedForEmployeeWithId(Long employeeId);

    Optional<SavedEmployer> findByIds(Long employeeId, Long employerId);

    Boolean checkIfSaved(Long id);

    List<EmployerReviewDto> getSavedEmployers();

    void deleteEmployerFromSaved(Long id);

    void addEmployerToSaved(Long id);

    EmployerReviewDto findEmployerWithIdByUser(Long id);
}
