package miwm.job4me.web.mappers.offer.parameters;

import miwm.job4me.model.offer.parameters.Level;
import miwm.job4me.web.model.offer.LevelDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class LevelMapperTest {
    @InjectMocks
    private LevelMapper levelMapper;

    private Level level;
    private LevelDto levelDto;
    private final Long ID = 1L;

    @BeforeEach
    void setUp() {
        level = Level.builder()
                .id(ID)
                .name("name")
                .build();

        levelDto = new LevelDto();
        levelDto.setId(level.getId());
        levelDto.setName(level.getName());
    }

    @Test
    @DisplayName("test toEntity - should return entity with the same values as dto")
    void toDto() {
        LevelDto levelDtoResult = levelMapper.toDto(level);

        assertEquals(levelDto.getId(), levelDtoResult.getId());
        assertEquals(levelDto.getName(), levelDtoResult.getName());
    }

    @Test
    @DisplayName("test toEntity - should return entity with the same values as dto")
    void toEntity() {
        Level levelResult = levelMapper.toEntity(levelDto);

        assertEquals(level.getId(), levelResult.getId());
        assertEquals(level.getName(), levelResult.getName());
    }

}
