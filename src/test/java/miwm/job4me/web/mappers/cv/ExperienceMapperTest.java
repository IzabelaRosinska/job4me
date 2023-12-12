package miwm.job4me.web.mappers.cv;

import miwm.job4me.model.cv.Experience;
import miwm.job4me.model.users.Employee;
import miwm.job4me.web.model.cv.ExperienceDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ExperienceMapperTest {
    @InjectMocks
    private ExperienceMapper experienceMapper;

    private Experience experience;

    private ExperienceDto experienceDto;

    private final Long ID = 1L;

    @BeforeEach
    void setUp() {
        Employee employee = Employee.builder().id(ID).build();

        experience = Experience.builder()
                .id(ID)
                .description("description")
                .employee(employee)
                .build();

        experienceDto = new ExperienceDto();
        experienceDto.setId(experience.getId());
        experienceDto.setDescription(experience.getDescription());
        experienceDto.setEmployeeId(experience.getEmployee().getId());
    }

    @Test
    @DisplayName("test toDto - should return dto with the same values as entity")
    void toDto() {
        ExperienceDto experienceDtoResult = experienceMapper.toDto(experience);

        assertEquals(experienceDto.getId(), experienceDtoResult.getId());
        assertEquals(experienceDto.getDescription(), experienceDtoResult.getDescription());
        assertEquals(experienceDto.getEmployeeId(), experienceDtoResult.getEmployeeId());
    }

    @Test
    @DisplayName("test toEntity - should return entity with the same values as dto")
    void toEntity() {
        Experience experienceResult = experienceMapper.toEntity(experienceDto);

        assertEquals(experience.getId(), experienceResult.getId());
        assertEquals(experience.getDescription(), experienceResult.getDescription());
    }

}
