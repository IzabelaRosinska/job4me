package miwm.job4me.web.mappers.cv;

import miwm.job4me.model.cv.Skill;
import miwm.job4me.model.users.Employee;
import miwm.job4me.web.model.cv.SkillDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class SkillMapperTest {
    @InjectMocks
    private SkillMapper skillMapper;

    private Skill skill;

    private SkillDto skillDto;

    private final Long ID = 1L;

    @BeforeEach
    void setUp() {
        Employee employee = Employee.builder().id(ID).build();

        skill = Skill.builder()
                .id(ID)
                .description("description")
                .employee(employee)
                .build();

        skillDto = new SkillDto();
        skillDto.setId(skill.getId());
        skillDto.setDescription(skill.getDescription());
        skillDto.setEmployeeId(employee.getId());
    }

    @Test
    @DisplayName("test toDto - should return dto with the same values as entity")
    void toDto() {
        SkillDto skillDtoResult = skillMapper.toDto(skill);

        assertEquals(skillDto.getId(), skillDtoResult.getId());
        assertEquals(skillDto.getDescription(), skillDtoResult.getDescription());
        assertEquals(skillDto.getEmployeeId(), skillDtoResult.getEmployeeId());
    }

    @Test
    @DisplayName("test toEntity - should return entity with the same values as dto")
    void toEntity() {
        Skill skillResult = skillMapper.toEntity(skillDto);

        assertEquals(skill.getId(), skillResult.getId());
        assertEquals(skill.getDescription(), skillResult.getDescription());
    }

}
