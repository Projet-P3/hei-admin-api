package school.hei.haapi.service.aws;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class S3conf {
    private final String bucketName;
    private final String env;

    public S3conf(
            @Value("${aws.bucket.name}")
            String bucketName,
            @Value("${env}")
            String env) {
        this.bucketName = bucketName;
        this.env = env;
    }
}
