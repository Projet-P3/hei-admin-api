package school.hei.haapi.service.aws;

import lombok.Getter;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
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
