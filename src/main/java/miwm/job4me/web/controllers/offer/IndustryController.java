package miwm.job4me.web.controllers.offer;

import io.swagger.v3.oas.annotations.Operation;
import miwm.job4me.services.offer.IndustryService;
import miwm.job4me.web.model.offer.IndustryDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class IndustryController {
    private final IndustryService industryService;

    public IndustryController(IndustryService industryService) {
        this.industryService = industryService;
    }

    @PostMapping("industries")
    @Operation(summary = "Create industry", description = "Creates new industry in database")
    public ResponseEntity<IndustryDto> createIndustry(@RequestBody IndustryDto industryDto) {
        return new ResponseEntity<>(industryService.saveDto(industryDto), HttpStatus.CREATED);
    }

    @GetMapping("industries")
    @Operation(summary = "Get all industries with pagination and filter", description = "Gets all industries from database with pagination and filter (by name)")
    public ResponseEntity<Page<IndustryDto>> getAllIndustries(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<IndustryDto> industryDtoPage = industryService.findByNameContaining(page, size, name);

        if (industryDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(industryDtoPage, HttpStatus.OK);
    }

    @GetMapping("industries/{id}")
    @Operation(summary = "Get industry by id", description = "Gets industry from database by id")
    public ResponseEntity<IndustryDto> getIndustryById(@PathVariable Long id) {
        return new ResponseEntity<>(industryService.findById(id), HttpStatus.OK);
    }

    @PutMapping("industries/{id}")
    @Operation(summary = "Update industry", description = "Updates industry in database")
    public ResponseEntity<IndustryDto> updateIndustry(@PathVariable Long id, @RequestBody IndustryDto industryDto) {
        return new ResponseEntity<>(industryService.update(id, industryDto), HttpStatus.CREATED);
    }

    @DeleteMapping("industries/{id}")
    @Operation(summary = "Delete industry", description = "Deletes industry from database")
    public ResponseEntity<Void> deleteIndustry(@PathVariable Long id) {
        industryService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
