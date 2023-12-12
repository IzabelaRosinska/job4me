package miwm.job4me.web.mappers.offer.parameters;

import miwm.job4me.model.offer.parameters.EmploymentForm;
import miwm.job4me.web.model.offer.EmploymentFormDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class EmploymentFormMapperTest {
    @InjectMocks
    private EmploymentFormMapper employmentFormMapper;

    private EmploymentForm employmentForm;
    private EmploymentFormDto employmentFormDto;
    private final Long ID = 1L;

    @BeforeEach
    void setUp() {
        employmentForm = EmploymentForm.builder()
                .id(ID)
                .name("name")
                .build();

        employmentFormDto = new EmploymentFormDto();
        employmentFormDto.setId(employmentForm.getId());
        employmentFormDto.setName(employmentForm.getName());
    }

    @Test
    @DisplayName("test toDto - should return dto with the same values as entity")
    void toDto() {
        EmploymentFormDto employmentFormDtoResult = employmentFormMapper.toDto(employmentForm);

        assertEquals(employmentFormDto.getId(), employmentFormDtoResult.getId());
        assertEquals(employmentFormDto.getName(), employmentFormDtoResult.getName());
    }

    @Test
    @DisplayName("test toEntity - should return entity with the same values as dto")
    void toEntity() {
        EmploymentForm employmentFormResult = employmentFormMapper.toEntity(employmentFormDto);

        assertEquals(employmentForm.getId(), employmentFormResult.getId());
        assertEquals(employmentForm.getName(), employmentFormResult.getName());
    }

}
