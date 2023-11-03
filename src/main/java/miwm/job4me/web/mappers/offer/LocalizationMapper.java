package miwm.job4me.web.mappers.offer;

import miwm.job4me.model.offer.Localization;
import miwm.job4me.web.model.offer.LocalizationDto;
import org.springframework.stereotype.Component;

@Component
public class LocalizationMapper {
    public LocalizationDto toDto(Localization localization) {
        LocalizationDto localizationDto = new LocalizationDto();
        localizationDto.setId(localization.getId());
        localizationDto.setCity(localization.getCity());
        return localizationDto;
    }

    public Localization toEntity(LocalizationDto localizationDto) {
        Localization localization = new Localization();
        localization.setId(localizationDto.getId());
        localization.setCity(localizationDto.getCity());
        return localization;
    }
}
