package miwm.job4me.services.aws;

public interface SnsNotificationService {
    void sendNotification(String topicArn, String message);
}
