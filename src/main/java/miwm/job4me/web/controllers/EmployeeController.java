package miwm.job4me.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import miwm.job4me.services.offer.SavedOfferService;
import miwm.job4me.services.users.EmployeeService;
import miwm.job4me.services.users.OrganizerService;
import miwm.job4me.services.users.SavedEmployerService;
import miwm.job4me.web.model.listDisplay.ListDisplayDto;
import miwm.job4me.web.model.offer.JobOfferReviewDto;
import miwm.job4me.web.model.users.EmployeeDto;
import miwm.job4me.web.model.users.EmployerReviewDto;
import miwm.job4me.web.model.users.OrganizerDto;
import miwm.job4me.web.model.users.QRDto;
import org.springframework.data.domain.Page;
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
    @Operation(summary = "Gets employee details", description = "Gets employee account details")
    public ResponseEntity<EmployeeDto> getEmployeeAccount() {
        return new ResponseEntity<>(employeeService.getEmployeeDetails(), HttpStatus.OK);
    }

    @PostMapping("account")
    @Operation(summary = "Save employee details", description = "Update employee account")
    public ResponseEntity<EmployeeDto> updateEmployeeAccount(@RequestBody EmployeeDto employeeDto) {
        return new ResponseEntity<>(employeeService.saveEmployeeDetails(employeeDto), HttpStatus.CREATED);
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

    @PostMapping("offer/{id}")
    public ResponseEntity<?> saveOfferForEmployee(@PathVariable Long id) {
        savedOfferService.addOfferToSaved(id);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @DeleteMapping("offer/{id}")
    public ResponseEntity<?> deleteOfferForEmployee(@PathVariable Long id) {
        savedOfferService.deleteOfferFromSaved(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("employer/{id}/account")
    @Operation(summary = "Gets employer with given id", description = "Gets employer with given id")
    public ResponseEntity<EmployerReviewDto> getEmployerWithIdForEmployee(@PathVariable Long id) {
        return new ResponseEntity<>(savedEmployerService.findEmployerWithIdByUser(id), HttpStatus.OK);
    }

    @PostMapping("employer/{id}")
    public ResponseEntity<?> saveEmployerForEmployee(@PathVariable Long id) {
        savedEmployerService.addEmployerToSaved(id);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @DeleteMapping("employer/{id}")
    public ResponseEntity<?> deleteEmployerForEmployee(@PathVariable Long id) {
        savedEmployerService.deleteEmployerFromSaved(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("employers")
    public ResponseEntity<List<EmployerReviewDto>> getSavedEmployers() {
        List<EmployerReviewDto> employers = savedEmployerService.getSavedEmployers();
        return new ResponseEntity<>(employers, HttpStatus.CREATED);
    }

    @GetMapping("saved-offers")
    public ResponseEntity<List<JobOfferReviewDto>> getSavedOffers() {
        List<JobOfferReviewDto> offers = savedOfferService.getSavedOffers();
        return new ResponseEntity<>(offers, HttpStatus.CREATED);
    }

    @GetMapping("employers/list-display")
    @Operation(summary = "Gets all saved employers in paginated list display form", description = "Gets all saved employers of logged in employee in paginated list display form database")
    public ResponseEntity<Page<ListDisplayDto>> getAllSavedEmployers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<ListDisplayDto> employerDtoPage = savedEmployerService.getSavedEmployersForEmployeeWithIdListDisplay(page, size);

        if (employerDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(employerDtoPage, HttpStatus.OK);
    }

    @GetMapping("job-offers/saved/list-display")
    @Operation(summary = "Gets all saved offers in paginated list display form", description = "Gets all saved offers of logged in employee in paginated list display form database")
    public ResponseEntity<Page<ListDisplayDto>> getAllSavedOffers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<ListDisplayDto> offerDtoPage = savedOfferService.getSavedJobOffersForEmployeeWithIdListDisplay(page, size);

        if (offerDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(offerDtoPage, HttpStatus.OK);
    }

    @GetMapping(value = "/code")
    public ResponseEntity<QRDto> getQRCode() throws Exception {
        return new ResponseEntity<>(employeeService.generateQRCodeImage(), HttpStatus.OK);
    }
}
