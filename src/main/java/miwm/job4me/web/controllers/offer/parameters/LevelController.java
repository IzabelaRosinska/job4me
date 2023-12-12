package miwm.job4me.web.controllers.offer.parameters;

import io.swagger.v3.oas.annotations.Operation;
import miwm.job4me.services.offer.parameters.LevelService;
import miwm.job4me.web.model.offer.LevelDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LevelController {
    private final LevelService levelService;

    public LevelController(LevelService levelService) {
        this.levelService = levelService;
    }

    @PostMapping("admin/levels")
    @Operation(summary = "Create level", description = "Creates new level in database")
    public ResponseEntity<LevelDto> createLevel(@RequestBody LevelDto levelDto) {
        return new ResponseEntity<>(levelService.saveDto(levelDto), HttpStatus.CREATED);
    }

    @GetMapping("levels")
    @Operation(summary = "Get all levels with pagination and filter", description = "Gets all levels from database with pagination and filter (by name)")
    public ResponseEntity<Page<LevelDto>> getAllLevels(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<LevelDto> levelDtoPage = levelService.findByNameContaining(page, size, name);

        if (levelDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(levelDtoPage, HttpStatus.OK);
    }

    @GetMapping("levels/{id}")
    @Operation(summary = "Get level by id", description = "Gets level from database by id")
    public ResponseEntity<LevelDto> getLevelById(@PathVariable Long id) {
        return new ResponseEntity<>(levelService.findById(id), HttpStatus.OK);
    }

    @PutMapping("admin/levels/{id}")
    @Operation(summary = "Update level", description = "Updates level in database")
    public ResponseEntity<LevelDto> updateLevel(@PathVariable Long id, @RequestBody LevelDto levelDto) {
        return new ResponseEntity<>(levelService.update(id, levelDto), HttpStatus.CREATED);
    }

    @DeleteMapping("admin/levels/{id}")
    @Operation(summary = "Delete level", description = "Deletes level from database")
    public ResponseEntity<Void> deleteLevel(@PathVariable Long id) {
        levelService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
