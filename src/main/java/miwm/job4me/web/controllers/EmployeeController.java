package miwm.job4me.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.services.offer.SavedOfferService;
import miwm.job4me.services.users.EmployeeService;
import miwm.job4me.services.users.OrganizerService;
import miwm.job4me.web.model.offer.JobOfferReviewDto;
import miwm.job4me.web.model.users.EmployeeDto;
import miwm.job4me.web.model.users.OrganizerDto;
import miwm.job4me.services.users.SavedEmployerService;
import miwm.job4me.web.model.users.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final OrganizerService organizerService;
    private final SavedOfferService savedOfferService;
    private final SavedEmployerService savedEmployerService;

    public EmployeeController(EmployeeService employeeService, OrganizerService organizerService, SavedOfferService savedOfferService, SavedEmployerService savedEmployerService) {
        this.employeeService = employeeService;
        this.organizerService = organizerService;
        this.savedOfferService = savedOfferService;
        this.savedEmployerService = savedEmployerService;
    }

    @GetMapping("account")
    public ResponseEntity<EmployeeDto> getEmployeeAccount() {
        EmployeeDto employeeDto;
        try {
            employeeDto = employeeService.getEmployeeDetails();
        } catch (NoSuchElementFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @PostMapping("account")
    public ResponseEntity<EmployeeDto> updateEmployeeAccount(@RequestBody EmployeeDto employeeDto) {
        try {
            employeeDto = employeeService.saveEmployeeDetails(employeeDto);
        } catch (NoSuchElementFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employeeDto, HttpStatus.CREATED);
    }

    @GetMapping("cv")
    @Operation(summary = "Gets current employee's CV", description = "Gets current employee's data restricted to CV scope")
    public ResponseEntity<EmployeeDto> getCV() {
        return new ResponseEntity<>(employeeService.findCurrentEmployee(), HttpStatus.OK);
    }

    @PutMapping("cv")
    @Operation(summary = "Updates current employee's CV", description = "Updates current employee's data restricted to CV scope")
    public ResponseEntity<EmployeeDto> updateCV(@RequestBody EmployeeDto employeeDto) {
        return new ResponseEntity<>(employeeService.updateCV(employeeDto), HttpStatus.OK);
    }

    @GetMapping("organizer/{id}/account")
    @Operation(summary = "Gets organizer with given id", description = "Gets organizer with given id")
    public ResponseEntity<OrganizerDto> getOrganizerWithIdForEmployee(@PathVariable Long id) {
        return new ResponseEntity<>(organizerService.findOrganizerById(id), HttpStatus.OK);
    }

    @GetMapping("job-offers/{id}")
    @Operation(summary = "Get job offer by id", description = "Gets job offer from database by id")
    public ResponseEntity<JobOfferReviewDto> getJobOfferReviewById(@PathVariable Long id) {
        return new ResponseEntity<>(savedOfferService.findOfferWithIdByUser(id), HttpStatus.OK);
    }

    @PostMapping("save-offer/{id}")
    public ResponseEntity<?> saveOfferForEmployee(@PathVariable Long id) {
        savedOfferService.addOfferToSaved(id);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @DeleteMapping("delete-offer/{id}")
    public ResponseEntity<?> deleteOfferForEmployee(@PathVariable Long id) {
        savedOfferService.deleteOfferFromSaved(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("employer/{id}/account")
    @Operation(summary = "Gets employer with given id", description = "Gets employer with given id")  
    public ResponseEntity<EmployerReviewDto> getEmployerWithIdForEmployee(@PathVariable Long id) {
        return new ResponseEntity<>(savedEmployerService.findEmployerWithIdByUser(id), HttpStatus.OK);
    }

    @PostMapping("save-employer/{id}")
    public ResponseEntity<?> saveEmployerForEmployee(@PathVariable Long id) {
        savedEmployerService.addEmployerToSaved(id);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @DeleteMapping("delete-employer/{id}")
    public ResponseEntity<?> deleteEmployerForEmployee(@PathVariable Long id) {
        savedEmployerService.deleteEmployerFromSaved(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("get-saved-employers")
    public ResponseEntity<List<EmployerReviewDto>> getSavedEmployers() {
        List<EmployerReviewDto> employers = savedEmployerService.getSavedEmployers();
        return new ResponseEntity<>(employers, HttpStatus.CREATED);
    }

    @GetMapping("get-saved-offers")
    public ResponseEntity<List<JobOfferReviewDto>> getSavedOffers() {
        List<JobOfferReviewDto> offers = savedOfferService.getSavedOffers();
        return new ResponseEntity<>(offers, HttpStatus.CREATED);
    }
}
