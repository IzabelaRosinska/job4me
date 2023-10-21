package miwm.job4me.services.cv;

import miwm.job4me.model.cv.Skill;
import miwm.job4me.services.BaseDtoService;
import miwm.job4me.web.model.cv.SkillDto;

import java.util.Set;

public interface SkillService extends BaseDtoService<Skill, SkillDto, Long> {
    SkillDto update(Skill object);

    Set<SkillDto> findAllByEmployeeId(Long id);

    void deleteAllByEmployeeId(Long id);

}
