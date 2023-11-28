package miwm.job4me.services.users;

import miwm.job4me.model.users.SavedEmployee;
import miwm.job4me.services.BaseService;
import miwm.job4me.web.model.listDisplay.ListDisplayDto;
import miwm.job4me.web.model.users.EmployeeReviewDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface SavedEmployeeService extends BaseService<SavedEmployee, Long> {

    List<SavedEmployee> getSavedForEmployerWithId(Long employerId);

    Page<SavedEmployee> getSavedEmployeesForEmployerWithId(int page, int size, Long employerId);

    Page<ListDisplayDto> getSavedEmployeesForEmployerWithIdListDisplay(int page, int size);

    Optional<SavedEmployee> findByIds(Long employerId, Long employeeId);

    Boolean checkIfSaved(Long id);

    List<EmployeeReviewDto> getSavedEmployees();

    void deleteEmployeeFromSaved(Long id);

    void addEmployeeToSaved(Long id);
    EmployeeReviewDto findEmployeeWithIdByUser(Long id);
}
