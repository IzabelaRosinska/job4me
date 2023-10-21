package miwm.job4me.web.mappers.cv;

import miwm.job4me.model.cv.Education;
import miwm.job4me.web.model.cv.EducationDto;
import org.springframework.stereotype.Component;

@Component
public class EducationMapper {

    public EducationDto toDto(Education education) {
        EducationDto educationDto = new EducationDto();
        educationDto.setId(education.getId());
        educationDto.setDescription(education.getDescription());
        educationDto.setEmployeeId(education.getEmployee().getId());
        return educationDto;
    }

    public Education toEntity(EducationDto educationDto) {
        Education education = new Education();
        education.setId(educationDto.getId());
        education.setDescription(educationDto.getDescription());
        return education;
    }
}
