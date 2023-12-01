package miwm.job4me.services.recommendation;

import miwm.job4me.exceptions.RecommendationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class RecommendationNotifierServiceImpl implements RecommendationNotifierService {
    @Value("${recommendation.api.url}")
    private String recommendationApiUrl;

    @Value("${recommendation.api.key}")
    private String recommendationApiKey;

    private final String RECOMMENDATION_EXCEPTION_MESSAGE = "Recommendation system failure";

    public RecommendationNotifierServiceImpl() {
    }

    @Override
    public void notifyUpdatedOffer(Long offerId) {
        String url = recommendationApiUrl + "/update-offer/" + offerId;
        notifyRecommendationService(url);
    }

    @Override
    public void notifyUpdatedEmployee(Long employeeId) {
        String url = recommendationApiUrl + "/update-employee/" + employeeId;
        notifyRecommendationService(url);
    }

    @Override
    public void notifyRemovedOffer(Long offerId) {
        String url = recommendationApiUrl + "/remove-offer/" + offerId;
        notifyRecommendationService(url);
    }

    @Override
    public void notifyRemovedEmployee(Long employeeId) {
        String url = recommendationApiUrl + "/remove-employee/" + employeeId;
        notifyRecommendationService(url);
    }

    @Override
    public void notifyRecommendationService(String url) {
        WebClient webClient = createHttpHeaders(url);

        ResponseEntity<String> responseEntity = webClient.get()
                .retrieve()
                .toEntity(String.class)
                .block();

        if (responseEntity == null || responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new RecommendationException(RECOMMENDATION_EXCEPTION_MESSAGE);
        }
    }

    private WebClient createHttpHeaders(String url) {
        return WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("api-key", recommendationApiKey)
                .build();
    }

}
