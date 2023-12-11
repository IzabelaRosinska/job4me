package miwm.job4me.services.offer.description;

import miwm.job4me.model.offer.description.ExtraSkill;
import miwm.job4me.services.BaseDtoService;
import miwm.job4me.web.model.offer.ExtraSkillDto;

import java.util.List;

public interface ExtraSkillService extends BaseDtoService<ExtraSkill, ExtraSkillDto, Long> {
    boolean existsById(Long id);

    void strictExistsById(Long id);

    ExtraSkillDto update(Long id, ExtraSkillDto extraSkill);

    List<ExtraSkillDto> findAllByJobOfferId(Long id);

    void deleteAllByJobOfferId(Long id);
}
