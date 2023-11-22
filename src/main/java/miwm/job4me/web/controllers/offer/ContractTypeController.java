package miwm.job4me.web.controllers.offer;

import io.swagger.v3.oas.annotations.Operation;
import miwm.job4me.services.offer.parameters.ContractTypeService;
import miwm.job4me.web.model.offer.ContractTypeDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContractTypeController {
    private final ContractTypeService contractTypeService;

    public ContractTypeController(ContractTypeService contractTypeService) {
        this.contractTypeService = contractTypeService;
    }

    @PostMapping("contract-types")
    @Operation(summary = "Create contract type", description = "Creates new contract type in database")
    public ResponseEntity<ContractTypeDto> createContractType(@RequestBody ContractTypeDto contractTypeDto) {
        return new ResponseEntity<>(contractTypeService.saveDto(contractTypeDto), HttpStatus.CREATED);
    }

    @GetMapping("contract-types")
    @Operation(summary = "Get all contract types with pagination and filter", description = "Gets all contract types from database with pagination and filter (by name)")
    public ResponseEntity<Page<ContractTypeDto>> getAllContractTypes(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<ContractTypeDto> contractTypeDtoPage = contractTypeService.findByNameContaining(page, size, name);

        if (contractTypeDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(contractTypeDtoPage, HttpStatus.OK);
    }

    @GetMapping("contract-types/{id}")
    @Operation(summary = "Get contract type by id", description = "Gets contract type from database by id")
    public ResponseEntity<ContractTypeDto> getContractTypeById(@PathVariable Long id) {
        return new ResponseEntity<>(contractTypeService.findById(id), HttpStatus.OK);
    }

    @PutMapping("contract-types/{id}")
    @Operation(summary = "Update contract type", description = "Updates contract type in database")
    public ResponseEntity<ContractTypeDto> updateContractType(@PathVariable Long id, @RequestBody ContractTypeDto contractTypeDto) {
        return new ResponseEntity<>(contractTypeService.update(id, contractTypeDto), HttpStatus.CREATED);
    }

    @DeleteMapping("contract-types/{id}")
    @Operation(summary = "Delete contract type", description = "Deletes contract type from database")
    public ResponseEntity<Void> deleteContractType(@PathVariable Long id) {
        contractTypeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
