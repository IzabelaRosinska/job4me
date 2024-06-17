package miwm.job4me.services.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SnsNotificationServiceImpl implements SnsNotificationService {

    @Value("${aws.access.key.id}")
    private String awsAccessKeyId;

    @Value("${aws.secret.access.key}")
    private String awsSecretAccessKey;

    @Value("${aws.session.token}")
    private String awsSessionToken;

    @Value("${aws.region}")
    private String awsRegion;

    private final AmazonSNS snsClient;

    public SnsNotificationServiceImpl() {
        this.snsClient = AmazonSNSClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicSessionCredentials(awsAccessKeyId, awsSecretAccessKey, awsSessionToken)))
                .withRegion(awsRegion)
                .build();
    }

    public void sendNotification(String topicArn, String message) {
        PublishRequest publishRequest = new PublishRequest(topicArn, message);
        PublishResult publishResult = snsClient.publish(publishRequest);

        System.out.println("MessageId - " + publishResult.getMessageId());
    }
}
