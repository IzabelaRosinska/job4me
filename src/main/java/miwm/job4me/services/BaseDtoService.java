package miwm.job4me.services;

import java.util.Set;

public interface BaseDtoService<ModelEntity, ModelDto, ID> {

    Set<ModelDto> findAll();

    ModelDto findById(ID id);

    ModelDto save(ModelEntity object);

    void delete(ModelEntity object);

    void deleteById(ID id);
}
