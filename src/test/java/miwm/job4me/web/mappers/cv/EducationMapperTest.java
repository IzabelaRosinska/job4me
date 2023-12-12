package miwm.job4me.web.mappers.cv;

import miwm.job4me.model.cv.Education;
import miwm.job4me.model.users.Employee;
import miwm.job4me.web.model.cv.EducationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class EducationMapperTest {
    @InjectMocks
    private EducationMapper educationMapper;

    private Education education;

    private EducationDto educationDto;

    private final Long ID = 1L;

    @BeforeEach
    void setUp() {
        Employee employee = Employee.builder().id(ID).build();

        education = Education.builder()
                .id(ID)
                .description("description")
                .employee(employee)
                .build();

        educationDto = new EducationDto();
        educationDto.setId(education.getId());
        educationDto.setDescription(education.getDescription());
        educationDto.setEmployeeId(education.getEmployee().getId());
    }

    @Test
    @DisplayName("test toDto - should return dto with the same values as entity")
    void toDto() {
        EducationDto educationDtoResult = educationMapper.toDto(education);

        assertEquals(educationDto.getId(), educationDtoResult.getId());
        assertEquals(educationDto.getDescription(), educationDtoResult.getDescription());
        assertEquals(educationDto.getEmployeeId(), educationDtoResult.getEmployeeId());
    }

    @Test
    @DisplayName("test toEntity - should return entity with the same values as dto")
    void toEntity() {
        Education educationResult = educationMapper.toEntity(educationDto);

        assertEquals(education.getId(), educationResult.getId());
        assertEquals(education.getDescription(), educationResult.getDescription());
    }

}
