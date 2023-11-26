package miwm.job4me.services.cv;

import miwm.job4me.model.cv.Education;
import miwm.job4me.services.BaseDtoService;
import miwm.job4me.web.model.cv.EducationDto;

import java.util.List;

public interface EducationService extends BaseDtoService<Education, EducationDto, Long> {
    void strictExistsById(Long id);

    boolean existsById(Long id);

    EducationDto update(EducationDto education);

    List<EducationDto> findAllByEmployeeId(Long id);

    void deleteAllByEmployeeId(Long id);
}
