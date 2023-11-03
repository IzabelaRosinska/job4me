package miwm.job4me.services.cv;

import miwm.job4me.model.cv.Experience;
import miwm.job4me.services.BaseDtoService;
import miwm.job4me.web.model.cv.ExperienceDto;

import java.util.Set;

public interface ExperienceService extends BaseDtoService<Experience, ExperienceDto, Long> {
    void strictExistsById(Long id);

    boolean existsById(Long id);

    ExperienceDto update(ExperienceDto experience);

    Set<ExperienceDto> findAllByEmployeeId(Long employeeId);

    void deleteAllByEmployeeId(Long employeeId);

}
