package miwm.job4me.services.recommendation;

import miwm.job4me.model.offer.JobOffer;
import miwm.job4me.services.users.EmployeeService;
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

@Service
public class RecommendationServiceImpl implements RecommendationService {
    @Value("${recommendation.api.url}")
    private String recommendationApiUrl;

    @Value("${recommendation.api.key}")
    private String recommendationApiKey;

    private final EmployeeService employeeService;

    public RecommendationServiceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public Page<JobOffer> getRecommendedOffers(int page, int size, String order) throws IOException, InterruptedException {
        getRecommendedOffersIds();
        return null;
    }

    private ArrayList<Long> getRecommendedOffersIds() throws IOException, InterruptedException {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(recommendationApiUrl + "/recommend/1/2"))
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

                return null;
            } else {
                System.out.println("Error: Unexpected response format");

                return null;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
