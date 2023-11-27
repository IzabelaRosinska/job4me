package miwm.job4me.services.offer.description;

import miwm.job4me.model.offer.Requirement;
import miwm.job4me.services.BaseDtoService;
import miwm.job4me.web.model.offer.RequirementDto;

import java.util.List;

public interface RequirementService extends BaseDtoService<Requirement, RequirementDto, Long> {
    boolean existsById(Long id);

    void strictExistsById(Long id);

    RequirementDto update(Long id, RequirementDto requirement);

    List<RequirementDto> findAllByJobOfferId(Long id);

    void deleteAllByJobOfferId(Long id);
}
