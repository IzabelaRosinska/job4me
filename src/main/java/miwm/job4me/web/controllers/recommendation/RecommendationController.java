package miwm.job4me.web.controllers.recommendation;

import io.swagger.v3.oas.annotations.Operation;
import miwm.job4me.services.recommendation.RecommendationService;
import miwm.job4me.web.model.filters.JobOfferFilterDto;
import miwm.job4me.web.model.listDisplay.ListDisplaySavedDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("employee/job-offers/list-display/job-fair/{jobFairId}/recommendation")
    @Operation(summary = "Get all job offers of job fair for logged in employee with recommendation", description = "Gets all job offers of job fair from database and returns them in list display saved dto with saved field set to true if job offer is saved by logged in employee with recommendation")
    public ResponseEntity<Page<ListDisplaySavedDto>> getAllOffersOfJobFairEmployeeView(@RequestParam(defaultValue = "0") int page,
                                                                                       @RequestParam(defaultValue = "10") int size,
                                                                                       @PathVariable Long jobFairId) {
        Page<ListDisplaySavedDto> listDisplaySavedDtoPage = recommendationService.getRecommendedOffers(page, size, jobFairId);

        if (listDisplaySavedDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(listDisplaySavedDtoPage, HttpStatus.OK);
    }

    @PostMapping("employee/job-offers/list-display/job-fair/{jobFairId}/recommendation/filter")
    @Operation(summary = "Get all job offers of job fair by filter (get with body) for logged in employee with recommendation", description = "Gets all job offers of job fair from database by filter and returns them in list display saved dto with saved field set to true if job offer is saved by logged in employee with recommendation")
    public ResponseEntity<Page<ListDisplaySavedDto>> getAllOffersOfJobFairByFilterEmployeeView(@RequestParam(defaultValue = "0") int page,
                                                                                               @RequestParam(defaultValue = "10") int size,
                                                                                               @RequestBody JobOfferFilterDto jobOfferFilterDto,
                                                                                               @PathVariable Long jobFairId) {
        Page<ListDisplaySavedDto> listDisplaySavedDtoPage = recommendationService.getRecommendedOffersByFilter(page, size, jobOfferFilterDto, jobFairId);

        if (listDisplaySavedDtoPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(listDisplaySavedDtoPage, HttpStatus.OK);
    }
}
