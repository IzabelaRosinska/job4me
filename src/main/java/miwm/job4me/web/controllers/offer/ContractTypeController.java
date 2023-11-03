package miwm.job4me.web.controllers.offer;

import miwm.job4me.services.offer.ContractTypeService;
import miwm.job4me.web.model.offer.ContractTypeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class ContractTypeController {
    private final ContractTypeService contractTypeService;

    public ContractTypeController(ContractTypeService contractTypeService) {
        this.contractTypeService = contractTypeService;
    }

    @GetMapping("contractTypes")
    public ResponseEntity<Set<ContractTypeDto>> getContractTypes() {
        return new ResponseEntity<>(contractTypeService.findAll(), HttpStatus.OK);
    }
}
