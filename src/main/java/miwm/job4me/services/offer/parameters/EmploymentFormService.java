package miwm.job4me.services.offer.parameters;

import miwm.job4me.model.offer.EmploymentForm;
import miwm.job4me.services.BaseDtoService;
import miwm.job4me.web.model.offer.EmploymentFormDto;
import org.springframework.data.domain.Page;

public interface EmploymentFormService extends BaseDtoService<EmploymentForm, EmploymentFormDto, Long> {
    Page<EmploymentFormDto> findByNameContaining(int page, int size, String name);

    EmploymentFormDto saveDto(EmploymentFormDto employmentForm);

    boolean existsByName(String name);

    void strictExistsByName(String name);

    boolean existsById(Long id);

    void strictExistsById(Long id);

    EmploymentFormDto update(Long id, EmploymentFormDto employmentForm);

    EmploymentForm findByName(String name);
}
