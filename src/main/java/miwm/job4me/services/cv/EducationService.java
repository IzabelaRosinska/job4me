package miwm.job4me.services.cv;

import miwm.job4me.model.cv.Education;
import miwm.job4me.services.BaseDtoService;
import miwm.job4me.web.model.cv.EducationDto;

import java.util.Set;

public interface EducationService extends BaseDtoService<Education, EducationDto, Long> {
    void strictExistsById(Long id);

    boolean existsById(Long id);

    EducationDto update(EducationDto education);

    Set<EducationDto> findAllByEmployeeId(Long id);

    void deleteAllByEmployeeId(Long id);
}
