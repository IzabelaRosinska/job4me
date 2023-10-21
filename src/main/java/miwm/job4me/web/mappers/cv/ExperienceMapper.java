package miwm.job4me.web.mappers.cv;

import miwm.job4me.model.cv.Experience;
import miwm.job4me.web.model.cv.ExperienceDto;
import org.springframework.stereotype.Component;

@Component
public class ExperienceMapper {

    public ExperienceDto toDto(Experience experience) {
        ExperienceDto experienceDto = new ExperienceDto();
        experienceDto.setId(experience.getId());
        experienceDto.setDescription(experience.getDescription());
        experienceDto.setEmployeeId(experience.getEmployee().getId());
        return experienceDto;
    }

    public Experience toEntity(ExperienceDto experienceDto) {
        Experience experience = new Experience();
        experience.setId(experienceDto.getId());
        experience.setDescription(experienceDto.getDescription());
        return experience;
    }
}
