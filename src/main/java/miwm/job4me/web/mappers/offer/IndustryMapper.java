package miwm.job4me.web.mappers.offer;

import miwm.job4me.model.offer.Industry;
import miwm.job4me.web.model.offer.IndustryDto;
import org.springframework.stereotype.Component;

@Component
public class IndustryMapper {
    public IndustryDto toDto(Industry industry) {
        IndustryDto industryDto = new IndustryDto();
        industryDto.setId(industry.getId());
        industryDto.setName(industry.getName());
        return industryDto;
    }

    public Industry toEntity(IndustryDto industryDto) {
        Industry industry = new Industry();
        industry.setId(industryDto.getId());
        industry.setName(industryDto.getName());
        return industry;
    }
}
