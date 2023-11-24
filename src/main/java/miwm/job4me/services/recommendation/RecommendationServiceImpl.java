package miwm.job4me.services.recommendation;

import miwm.job4me.exceptions.RecommendationException;
import miwm.job4me.services.event.JobFairService;
import miwm.job4me.services.offer.listDisplay.JobOfferListDisplayService;
import miwm.job4me.services.users.EmployeeService;
import miwm.job4me.web.model.filters.JobOfferFilterDto;
import miwm.job4me.web.model.listDisplay.ListDisplaySavedDto;
import org.json.JSONArray;
import org.json.JSONObject;
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

        return jobOfferListDisplayService
                .findAllRecommendedOffersEmployeeView(page, size, recommendedOffersIds);
    }

    @Override
    public Page<ListDisplaySavedDto> getRecommendedOffersByFilter(int page, int size, JobOfferFilterDto jobOfferFilterDto, Long jobFairId) {
        jobFairService.strictExistsById(jobFairId);
        Long employeeId = employeeService.getAuthEmployee().getId();

        List<Long> recommendedOffersIds = getRecommendedOffersIds(employeeId, jobFairId);

        return jobOfferListDisplayService
                .findAllRecommendedOffersByFilterEmployeeView(page, size, jobOfferFilterDto, recommendedOffersIds);
    }

    private ArrayList<Long> getRecommendedOffersIds(Long employeeId, Long jobFairId) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(recommendationApiUrl + "/recommend/" + jobFairId + "/" + employeeId))
                    .header("Content-Type", "application/json")
                    .header("API-Key", recommendationApiKey)
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonResponse = new JSONObject(response.body());

            if (jsonResponse.has("data")) {
                JSONArray dataArray = jsonResponse.getJSONArray("data");
                ArrayList<Long> dataList = new ArrayList<>();

                for (int i = 0; i < dataArray.length(); i++) {
                    dataList.add(dataArray.getLong(i));
                }

                System.out.println("Success: " + dataList);

                return dataList;
            } else if (jsonResponse.has("error")) {
                String errorMessage = jsonResponse.getString("error");
                System.out.println("Error: " + errorMessage);

                throw new RecommendationException(errorMessage);
            } else {
                System.out.println("Error: Unexpected response format");

                throw new RecommendationException("Unexpected response format");
            }
        } catch (IOException | InterruptedException e) {
            throw new RecommendationException(RECOMMENDATION_EXCEPTION_MESSAGE);
        }
    }
}
