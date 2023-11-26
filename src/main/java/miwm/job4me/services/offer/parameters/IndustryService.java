package miwm.job4me.services.offer.parameters;

import miwm.job4me.model.offer.Industry;
import miwm.job4me.services.BaseDtoService;
import miwm.job4me.web.model.offer.IndustryDto;
import org.springframework.data.domain.Page;

public interface IndustryService extends BaseDtoService<Industry, IndustryDto, Long> {
    Page<IndustryDto> findByNameContaining(int page, int size, String name);

    IndustryDto saveDto(IndustryDto industry);

    boolean existsByName(String name);

    void strictExistsByName(String name);

    boolean existsById(Long id);

    void strictExistsById(Long id);

    IndustryDto update(Long id, IndustryDto industry);

    Industry findByName(String name);
}
