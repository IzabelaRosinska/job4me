package miwm.job4me.web.mappers.offer.parameters;

import miwm.job4me.model.offer.parameters.Localization;
import miwm.job4me.web.model.offer.LocalizationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class LocalizationMapperTest {
    @InjectMocks
    private LocalizationMapper localizationMapper;

    private Localization localization;
    private LocalizationDto localizationDto;
    private final Long ID = 1L;

    @BeforeEach
    void setUp() {
        localization = Localization.builder()
                .id(ID)
                .city("city")
                .build();

        localizationDto = new LocalizationDto();
        localizationDto.setId(localization.getId());
        localizationDto.setCity(localization.getCity());
    }

    @Test
    @DisplayName("test toDto - should return dto with the same values as entity")
    void toDto() {
        LocalizationDto localizationDtoResult = localizationMapper.toDto(localization);

        assertEquals(localizationDto.getId(), localizationDtoResult.getId());
        assertEquals(localizationDto.getCity(), localizationDtoResult.getCity());
    }

    @Test
    @DisplayName("test toEntity - should return entity with the same values as dto")
    void toEntity() {
        Localization localizationResult = localizationMapper.toEntity(localizationDto);

        assertEquals(localization.getId(), localizationResult.getId());
        assertEquals(localization.getCity(), localizationResult.getCity());
    }

}
