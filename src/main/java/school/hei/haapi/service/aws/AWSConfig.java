package school.hei.haapi.service.aws;

import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;

@Configuration
public class AWSConfig {
    public AwsCredentials credentials () {
        AwsCredentials credentials = new AwsBasicCredentials(
                "${}"
        );
    }
}
