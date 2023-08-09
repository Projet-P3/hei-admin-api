package school.hei.haapi.service.aws;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@Getter
public class S3conf {
    private String bucketName;

    private String env;

    public S3Conf(
            @Value("${aws.bucket.name}")
            String bucketName,
            @Value("${env}")
            String env
    ) {
        this.bucketName=bucketName;
        this.env=env;
    }



    private final String awsS3AccessKeyId = System.getenv("AWS_ACCESS_KEY_ID");

    private final String awsS3SecretKey =  System.getenv("AWS_SECRET_ACCESS_KEY");

    private final String awsS3Region =  System.getenv("AWS_REGION");

    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(awsS3AccessKeyId, awsS3SecretKey);

        return S3Client.builder()
                .credentialsProvider(() -> credentials)
                .region(Region.of(awsS3Region))
                .build();
    }

}
