package miwm.job4me.web.controllers;

import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.services.users.OrganizerService;
import miwm.job4me.web.model.users.OrganizerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("organizer")
public class OrganizerController {

    private final OrganizerService organizerService;

    public OrganizerController(OrganizerService organizerService) {
        this.organizerService = organizerService;
    }

    @GetMapping("account")
    public ResponseEntity<OrganizerDto> getOrganizerAccount() {
        OrganizerDto organizerDto;
        try {
            organizerDto = organizerService.getOrganizerDetails();
        } catch (NoSuchElementFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(organizerDto, HttpStatus.OK);
    }

    @PostMapping("account")
    public ResponseEntity<OrganizerDto> updateOrganizerAccount(@RequestBody OrganizerDto organizerDto) {
        try {
            organizerDto = organizerService.saveOrganizerDetails(organizerDto);
        } catch (NoSuchElementFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(organizerDto, HttpStatus.CREATED);
    }
}
