package miwm.job4me.web.controllers.recommendation;

import miwm.job4me.services.recommendation.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/recommendation")
    public ResponseEntity<?> getRecommendedOffers() {
        try {
            return ResponseEntity.ok(recommendationService.getRecommendedOffers(0, 10, "desc"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
