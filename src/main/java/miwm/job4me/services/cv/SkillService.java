package miwm.job4me.services.cv;

import miwm.job4me.model.cv.Skill;
import miwm.job4me.services.BaseDtoService;
import miwm.job4me.web.model.cv.SkillDto;

import java.util.List;

public interface SkillService extends BaseDtoService<Skill, SkillDto, Long> {
    void strictExistsById(Long id);

    boolean existsById(Long id);

    SkillDto update(SkillDto skill);

    List<SkillDto> findAllByEmployeeId(Long id);

    void deleteAllByEmployeeId(Long id);

}
