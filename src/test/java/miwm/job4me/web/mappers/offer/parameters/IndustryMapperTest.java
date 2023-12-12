package miwm.job4me.web.mappers.offer.parameters;

import miwm.job4me.model.offer.parameters.Industry;
import miwm.job4me.web.model.offer.IndustryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class IndustryMapperTest {
    @InjectMocks
    private IndustryMapper industryMapper;

    private Industry industry;
    private IndustryDto industryDto;
    private final Long ID = 1L;

    @BeforeEach
    void setUp() {
        industry = Industry.builder()
                .id(ID)
                .name("name")
                .build();

        industryDto = new IndustryDto();
        industryDto.setId(industry.getId());
        industryDto.setName(industry.getName());
    }

    @Test
    @DisplayName("test toDto - should return dto with the same values as entity")
    void toDto() {
        IndustryDto industryDtoResult = industryMapper.toDto(industry);

        assertEquals(industryDto.getId(), industryDtoResult.getId());
        assertEquals(industryDto.getName(), industryDtoResult.getName());
    }

    @Test
    @DisplayName("test toEntity - should return entity with the same values as dto")
    void toEntity() {
        Industry industryResult = industryMapper.toEntity(industryDto);

        assertEquals(industry.getId(), industryResult.getId());
        assertEquals(industry.getName(), industryResult.getName());
    }
}
