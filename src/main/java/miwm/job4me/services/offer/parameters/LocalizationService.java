package miwm.job4me.services.offer.parameters;

import miwm.job4me.model.offer.Localization;
import miwm.job4me.services.BaseDtoService;
import miwm.job4me.web.model.offer.LocalizationDto;
import org.springframework.data.domain.Page;

public interface LocalizationService extends BaseDtoService<Localization, LocalizationDto, Long> {
    Page<LocalizationDto> findByCityContaining(int page, int size, String city);

    LocalizationDto saveDto(LocalizationDto Localization);

    boolean existsByCity(String city);

    void strictExistsByCity(String city);

    boolean existsById(Long id);

    void strictExistsById(Long id);

    LocalizationDto update(Long id, LocalizationDto localization);

    Localization findByCity(String city);
}
