package miwm.job4me.services.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SnsNotificationServiceImpl implements SnsNotificationService {

    private final AmazonSNS snsClient;

    public SnsNotificationServiceImpl(
            @Value("${aws.access.key.id}") String awsAccessKeyId,
            @Value("${aws.secret.access.key}") String awsSecretAccessKey,
            @Value("${aws.session.token}") String awsSessionToken) {
        this.snsClient = AmazonSNSClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicSessionCredentials(awsAccessKeyId, awsSecretAccessKey, awsSessionToken)))
                .withRegion(Regions.US_EAST_1)
                .build();
    }

    public void sendNotification(String topicArn, String message) {
        PublishRequest publishRequest = new PublishRequest(topicArn, message);
        PublishResult publishResult = snsClient.publish(publishRequest);

        System.out.println("MessageId - " + publishResult.getMessageId());
    }
}
