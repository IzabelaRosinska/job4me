package miwm.job4me.web.mappers.offer.parameters;

import miwm.job4me.model.offer.parameters.ContractType;
import miwm.job4me.web.model.offer.ContractTypeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ContractTypeMapperTest {
    @InjectMocks
    private ContractTypeMapper contractTypeMapper;

    private ContractType contractType;
    private ContractTypeDto contractTypeDto;
    private final Long ID = 1L;

    @BeforeEach
    void setUp() {
        contractType = ContractType.builder()
                .id(ID)
                .name("name")
                .build();

        contractTypeDto = new ContractTypeDto();
        contractTypeDto.setId(contractType.getId());
        contractTypeDto.setName(contractType.getName());
    }

    @Test
    @DisplayName("test toDto - should return dto with the same values as entity")
    void toDto() {
        ContractTypeDto contractTypeDtoResult = contractTypeMapper.toDto(contractType);

        assertEquals(contractTypeDto.getId(), contractTypeDtoResult.getId());
        assertEquals(contractTypeDto.getName(), contractTypeDtoResult.getName());
    }

    @Test
    @DisplayName("test toEntity - should return entity with the same values as dto")
    void toEntity() {
        ContractType contractTypeResult = contractTypeMapper.toEntity(contractTypeDto);

        assertEquals(contractType.getId(), contractTypeResult.getId());
        assertEquals(contractType.getName(), contractTypeResult.getName());
    }
}
