package miwm.job4me.services.recommendation;

import miwm.job4me.model.offer.JobOffer;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface RecommendationService {
    Page<JobOffer> getRecommendedOffers(int page, int size, String order) throws IOException, InterruptedException;
}
