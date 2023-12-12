package miwm.job4me.web.mappers.offer.description;

import miwm.job4me.model.offer.JobOffer;
import miwm.job4me.model.offer.description.Requirement;
import miwm.job4me.web.model.offer.RequirementDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class RequirementMapperTest {
    @InjectMocks
    private RequirementMapper requirementMapper;

    private Requirement requirement;
    private RequirementDto requirementDto;
    private final Long ID = 1L;

    @BeforeEach
    void setUp() {
        JobOffer jobOffer = JobOffer.builder().id(ID).build();

        requirement = Requirement.builder()
                .id(ID)
                .description("description")
                .jobOffer(jobOffer)
                .build();

        requirementDto = new RequirementDto();
        requirementDto.setId(requirement.getId());
        requirementDto.setDescription(requirement.getDescription());
    }

    @Test
    @DisplayName("test toDto - should return dto with the same values as entity")
    void toDto() {
        RequirementDto requirementDtoResult = requirementMapper.toDto(requirement);

        assertEquals(requirementDto.getId(), requirementDtoResult.getId());
        assertEquals(requirementDto.getDescription(), requirementDtoResult.getDescription());
    }

    @Test
    @DisplayName("test toEntity - should return entity with the same values as dto")
    void toEntity() {
        Requirement requirementResult = requirementMapper.toEntity(requirementDto);

        assertEquals(requirement.getId(), requirementResult.getId());
        assertEquals(requirement.getDescription(), requirementResult.getDescription());
    }
}
