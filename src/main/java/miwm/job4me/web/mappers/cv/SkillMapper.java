package miwm.job4me.web.mappers.cv;

import miwm.job4me.model.cv.Skill;
import miwm.job4me.web.model.cv.SkillDto;
import org.springframework.stereotype.Component;

@Component
public class SkillMapper {

    public SkillDto toDto(Skill skill) {
        SkillDto skillDto = new SkillDto();
        skillDto.setId(skill.getId());
        skillDto.setDescription(skill.getDescription());
        skillDto.setEmployeeId(skill.getEmployee().getId());
        return skillDto;
    }

    public Skill toEntity(SkillDto skillDto) {
        Skill skill = new Skill();
        skill.setId(skillDto.getId());
        skill.setDescription(skillDto.getDescription());
        return skill;
    }
}
