package miwm.job4me.web.controllers.offer.parameters;

import io.swagger.v3.oas.annotations.Operation;
import miwm.job4me.services.offer.parameters.LocalizationService;
import miwm.job4me.web.model.offer.LocalizationDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LocalizationController {
    private final LocalizationService localizationService;

    public LocalizationController(LocalizationService localizationService) {
        this.localizationService = localizationService;
    }

    @PostMapping("admin/localizations")
    @Operation(summary = "Create localization", description = "Creates new localization in database")
    public ResponseEntity<LocalizationDto> createLocalization(@RequestBody LocalizationDto localizationDto) {
        return new ResponseEntity<>(localizationService.saveDto(localizationDto), HttpStatus.CREATED);
    }

    @GetMapping("localizations")
    @Operation(summary = "Get all localizations", description = "Gets all localizations from database")
    public ResponseEntity<Page<LocalizationDto>> getAllLocalizations(
            @RequestParam(defaultValue = "") String city,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<LocalizationDto> localizationDtoPage = localizationService.findByCityContaining(page, size, city);

        if (localizationDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(localizationDtoPage, HttpStatus.OK);
    }

    @GetMapping("localizations/{id}")
    @Operation(summary = "Get localization by id", description = "Gets localization from database by id")
    public ResponseEntity<LocalizationDto> getLocalizationById(@PathVariable Long id) {
        return new ResponseEntity<>(localizationService.findById(id), HttpStatus.OK);
    }

    @PutMapping("admin/localizations/{id}")
    @Operation(summary = "Update localization", description = "Updates localization in database")
    public ResponseEntity<LocalizationDto> updateLocalization(@PathVariable Long id, @RequestBody LocalizationDto localizationDto) {
        return new ResponseEntity<>(localizationService.update(id, localizationDto), HttpStatus.CREATED);
    }

    @DeleteMapping("admin/localizations/{id}")
    @Operation(summary = "Delete localization", description = "Deletes localization from database")
    public ResponseEntity<Void> deleteLocalization(@PathVariable Long id) {
        localizationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
