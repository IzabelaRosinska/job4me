package miwm.job4me.services.recommendation;

import miwm.job4me.exceptions.RecommendationException;
import miwm.job4me.services.event.JobFairService;
import miwm.job4me.services.offer.listDisplay.JobOfferListDisplayService;
import miwm.job4me.services.pagination.ListDisplaySavedPageServiceImpl;
import miwm.job4me.services.users.EmployeeService;
import miwm.job4me.validators.pagination.PaginationValidator;
import miwm.job4me.web.model.filters.JobOfferFilterDto;
import miwm.job4me.web.model.listDisplay.ListDisplaySavedDto;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
    private final ListDisplaySavedPageServiceImpl listDisplaySavedPageServiceImpl;
    private final PaginationValidator paginationValidator;
    private final String RECOMMENDATION_EXCEPTION_MESSAGE = "Recommendation system failure";


    public RecommendationServiceImpl(EmployeeService employeeService, JobFairService jobFairService, JobOfferListDisplayService jobOfferListDisplayService, ListDisplaySavedPageServiceImpl listDisplaySavedPageServiceImpl, PaginationValidator paginationValidator) {
        this.employeeService = employeeService;
        this.jobFairService = jobFairService;
        this.jobOfferListDisplayService = jobOfferListDisplayService;
        this.listDisplaySavedPageServiceImpl = listDisplaySavedPageServiceImpl;
        this.paginationValidator = paginationValidator;
    }

    @Override
    public Page<ListDisplaySavedDto> getRecommendedOffers(int page, int size, Long jobFairId) {
        jobFairService.strictExistsById(jobFairId);
        Long employeeId = employeeService.getAuthEmployee().getId();
        paginationValidator.validatePagination(size, page);

        List<Long> recommendedOffersIds = getRecommendedOffersIds(employeeId, jobFairId);
        int numberOfOffers = recommendedOffersIds.size();
        recommendedOffersIds = createSublist(recommendedOffersIds, size, page);

        List<ListDisplaySavedDto> recommendedOffers = recommendedOffersIds
                .stream()
                .map(jobOfferListDisplayService::findByOfferId)
                .toList();

        return listDisplaySavedPageServiceImpl.createPageGivenSublist(recommendedOffers, size, page, numberOfOffers);
    }

    @Override
    public Page<ListDisplaySavedDto> getRecommendedOffersByFilter(int page, int size, JobOfferFilterDto jobOfferFilterDto, Long jobFairId) {
        jobFairService.strictExistsById(jobFairId);
        Long employeeId = employeeService.getAuthEmployee().getId();

        List<Long> recommendedOffersIds = getRecommendedOffersIds(employeeId, jobFairId);
        int numberOfOffers = recommendedOffersIds.size();
        List<Long> filteredOffersIds = jobOfferListDisplayService
                .findAllOffersByFilterEmployeeView(page, size, "1", jobOfferFilterDto)
                .map(ListDisplaySavedDto::getId)
                .toList();

        for (Long id : filteredOffersIds) {
            if (!recommendedOffersIds.contains(id)) {
                recommendedOffersIds.remove(id);
            }
        }

        recommendedOffersIds = createSublist(recommendedOffersIds, size, page);

        List<ListDisplaySavedDto> recommendedOffers = recommendedOffersIds
                .stream()
                .map(jobOfferListDisplayService::findByOfferId)
                .toList();

        return listDisplaySavedPageServiceImpl.createPageGivenSublist(recommendedOffers, size, page, numberOfOffers);
    }

    private List<Long> createSublist(List<Long> list, int pageSize, int pageNumber) {
        int start = pageNumber * pageSize;
        int end = Math.min((start + pageSize), list.size());
        return list.subList(start, end);
    }

    private WebClient createHttpHeaders(String url) {
        return WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("api-key", recommendationApiKey)
                .build();
    }

    private List<Long> getRecommendedOffersIds(Long employeeId, Long jobFairId) {
        String url = recommendationApiUrl + "/recommend/" + jobFairId + "/" + employeeId;

        WebClient webClient = createHttpHeaders(url);

        String response = webClient.get()
                .retrieve()
                .bodyToMono(String.class)
                .block();

        if (response == null || response.equals("")) {
            throw new RecommendationException(RECOMMENDATION_EXCEPTION_MESSAGE);
        } else {
            JSONArray jsonArray = new JSONArray(response);
            List<Long> recommendedOffersIds = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                recommendedOffersIds.add(jsonArray.getLong(i));
            }

            System.out.println("recommendedOffersIds: " + recommendedOffersIds);

            return recommendedOffersIds;
        }
    }

}
