package miwm.job4me.web.mappers.offer.description;

import miwm.job4me.model.offer.JobOffer;
import miwm.job4me.model.offer.description.ExtraSkill;
import miwm.job4me.web.model.offer.ExtraSkillDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ExtraSkillMapperTest {
    @InjectMocks
    private ExtraSkillMapper extraSkillMapper;

    private ExtraSkill extraSkill;

    private ExtraSkillDto extraSkillDto;

    private final Long ID = 1L;

    @BeforeEach
    void setUp() {
        JobOffer jobOffer = JobOffer.builder().id(ID).build();

        extraSkill = ExtraSkill.builder()
                .id(ID)
                .description("description")
                .jobOffer(jobOffer)
                .build();

        extraSkillDto = new ExtraSkillDto();
        extraSkillDto.setId(extraSkill.getId());
        extraSkillDto.setDescription(extraSkill.getDescription());
    }

    @Test
    @DisplayName("test toDto - should return dto with the same values as entity")
    void toDto() {
        ExtraSkillDto extraSkillDtoResult = extraSkillMapper.toDto(extraSkill);

        assertEquals(extraSkillDto.getId(), extraSkillDtoResult.getId());
        assertEquals(extraSkillDto.getDescription(), extraSkillDtoResult.getDescription());
    }

    @Test
    @DisplayName("test toEntity - should return entity with the same values as dto")
    void toEntity() {
        ExtraSkill extraSkillResult = extraSkillMapper.toEntity(extraSkillDto);

        assertEquals(extraSkill.getId(), extraSkillResult.getId());
        assertEquals(extraSkill.getDescription(), extraSkillResult.getDescription());
    }
}
