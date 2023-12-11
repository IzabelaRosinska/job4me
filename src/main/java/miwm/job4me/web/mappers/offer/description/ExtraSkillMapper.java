package miwm.job4me.web.mappers.offer.description;

import miwm.job4me.model.offer.description.ExtraSkill;
import miwm.job4me.web.model.offer.ExtraSkillDto;
import org.springframework.stereotype.Component;

@Component
public class ExtraSkillMapper {
    public ExtraSkillDto toDto(ExtraSkill extraSkill) {
        ExtraSkillDto extraSkillDto = new ExtraSkillDto();
        extraSkillDto.setId(extraSkill.getId());
        extraSkillDto.setDescription(extraSkill.getDescription());
        return extraSkillDto;
    }

    public ExtraSkill toEntity(ExtraSkillDto extraSkillDto) {
        ExtraSkill extraSkill = new ExtraSkill();
        extraSkill.setId(extraSkillDto.getId());
        extraSkill.setDescription(extraSkillDto.getDescription());
        return extraSkill;
    }
}
