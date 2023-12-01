package miwm.job4me.services.recommendation;

public interface RecommendationNotifierService {

    void notifyRecommendationService(String url);

    void notifyUpdatedOffer(Long offerId);

    void notifyUpdatedEmployee(Long employeeId);

    void notifyRemovedOffer(Long offerId);

    void notifyRemovedEmployee(Long employeeId);
}
