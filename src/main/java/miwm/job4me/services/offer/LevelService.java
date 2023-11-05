package miwm.job4me.services.offer;

import miwm.job4me.model.offer.Level;
import miwm.job4me.services.BaseDtoService;
import miwm.job4me.web.model.offer.LevelDto;
import org.springframework.data.domain.Page;

public interface LevelService extends BaseDtoService<Level, LevelDto, Long> {
    Page<LevelDto> findByNameContaining(int page, int size, String name);

    LevelDto saveDto(LevelDto level);

    boolean existsByName(String name);

    void strictExistsByName(String name);

    boolean existsById(Long id);

    void strictExistsById(Long id);

    LevelDto update(Long id, LevelDto level);

    Level findByName(String name);
}
