package miwm.job4me.web.mappers.offer.parametrs;

import miwm.job4me.model.offer.parameters.Level;
import miwm.job4me.web.model.offer.LevelDto;
import org.springframework.stereotype.Component;

@Component
public class LevelMapper {
    public LevelDto toDto(Level level) {
        LevelDto levelDto = new LevelDto();
        levelDto.setId(level.getId());
        levelDto.setName(level.getName());
        return levelDto;
    }

    public Level toEntity(LevelDto levelDto) {
        Level level = new Level();
        level.setId(levelDto.getId());
        level.setName(levelDto.getName());
        return level;
    }
}
