package school.hei.haapi.service.aws;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
@Getter
public class S3Conf {
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

  @Value("${aws.region}")
  private String awsS3Region;

  @Bean
  public S3Client s3Client() {
    AwsBasicCredentials credentials = AwsBasicCredentials.create(System.getenv("AWS_ACCESS_KEY_ID"), System.getenv("AWS_SECRET_ACCESS_KEY"));

    return S3Client.builder()
      .credentialsProvider(() -> credentials)
      .region(Region.of(awsS3Region))
      .build();
  }

}
