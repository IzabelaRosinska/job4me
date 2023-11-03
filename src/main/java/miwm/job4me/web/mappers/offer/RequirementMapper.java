package miwm.job4me.web.mappers.offer;

import miwm.job4me.model.offer.Requirement;
import miwm.job4me.web.model.offer.RequirementDto;
import org.springframework.stereotype.Component;

@Component
public class RequirementMapper {
    public RequirementDto toDto(Requirement requirement) {
        RequirementDto requirementDto = new RequirementDto();
        requirementDto.setId(requirement.getId());
        requirementDto.setDescription(requirement.getDescription());
        return requirementDto;
    }

    public Requirement toEntity(RequirementDto requirementDto) {
        Requirement requirement = new Requirement();
        requirement.setId(requirementDto.getId());
        requirement.setDescription(requirementDto.getDescription());
        return requirement;
    }
}
