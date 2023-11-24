package miwm.job4me.services.recommendation;

import miwm.job4me.exceptions.RecommendationException;
import miwm.job4me.services.event.JobFairService;
import miwm.job4me.services.offer.listDisplay.JobOfferListDisplayService;
import miwm.job4me.services.users.EmployeeService;
import miwm.job4me.web.model.filters.JobOfferFilterDto;
import miwm.job4me.web.model.listDisplay.ListDisplaySavedDto;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationService {
    @Value("${recommendation.api.url}")
    private String recommendationApiUrl;

    @Value("${recommendation.api.key}")
    private String recommendationApiKey;

    private final EmployeeService employeeService;
    private final JobFairService jobFairService;
    private final JobOfferListDisplayService jobOfferListDisplayService;
    private final String RECOMMENDATION_EXCEPTION_MESSAGE = "Unexpected response format";


    public RecommendationServiceImpl(EmployeeService employeeService, JobFairService jobFairService, JobOfferListDisplayService jobOfferListDisplayService) {
        this.employeeService = employeeService;
        this.jobFairService = jobFairService;
        this.jobOfferListDisplayService = jobOfferListDisplayService;
    }

    @Override
    public Page<ListDisplaySavedDto> getRecommendedOffers(int page, int size, Long jobFairId) {
        jobFairService.strictExistsById(jobFairId);
        Long employeeId = employeeService.getAuthEmployee().getId();

        List<Long> recommendedOffersIds = getRecommendedOffersIds(employeeId, jobFairId);

        return jobOfferListDisplayService.findAllById(page, size, recommendedOffersIds);
    }

    @Override
    public Page<ListDisplaySavedDto> getRecommendedOffersByFilter(int page, int size, JobOfferFilterDto jobOfferFilterDto, Long jobFairId) {
        jobFairService.strictExistsById(jobFairId);
        Long employeeId = employeeService.getAuthEmployee().getId();

        List<Long> recommendedOffersIds = getRecommendedOffersIds(employeeId, jobFairId);
        List<Long> filteredOffersIds = jobOfferListDisplayService
                .findAllOffersByFilterEmployeeView(page, size, "1", jobOfferFilterDto)
                .map(ListDisplaySavedDto::getId)
                .toList();

        for (Long id : filteredOffersIds) {
            if (!recommendedOffersIds.contains(id)) {
                recommendedOffersIds.remove(id);
            }
        }

        return jobOfferListDisplayService
                .findAllById(page, size, recommendedOffersIds);
    }

    private List<Long> getRecommendedOffersIds(Long employeeId, Long jobFairId) {
        String url = recommendationApiUrl + "/recommend/" + jobFairId + "/" + employeeId;

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .header("API-Key", recommendationApiKey)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                JSONArray jsonArray = new JSONArray(response.body());
                List<Long> recommendedOffersIds = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    recommendedOffersIds.add(jsonArray.getLong(i));
                }
                System.out.println(recommendedOffersIds);
                return recommendedOffersIds;
            } else {
                System.out.println("ODPOWIEDŹ: " + response.statusCode());
                throw new RecommendationException(RECOMMENDATION_EXCEPTION_MESSAGE);
            }


        } catch (IOException | InterruptedException e) {
            System.out.println("BŁĄD");
            e.printStackTrace();
        }

        return null;
    }
}
