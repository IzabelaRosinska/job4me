package miwm.job4me.services.offer.description;

import miwm.job4me.model.offer.ExtraSkill;
import miwm.job4me.services.BaseDtoService;
import miwm.job4me.web.model.offer.ExtraSkillDto;

import java.util.Set;

public interface ExtraSkillService extends BaseDtoService<ExtraSkill, ExtraSkillDto, Long> {
    boolean existsById(Long id);

    void strictExistsById(Long id);

    ExtraSkillDto update(Long id, ExtraSkillDto extraSkill);

    Set<ExtraSkillDto> findAllByJobOfferId(Long id);

    void deleteAllByJobOfferId(Long id);
}
