package school.hei.haapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
//import com.amazonaws.services.s3.AmazonS3;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;
import software.amazon.awssdk.services.s3.model.S3Object;

@Configuration
public class S3Conf {
//
//    @Value("${aws.access.key.id}")
//    private String accessKey;
//
//    @Value("${aws.secret.access.key}")
//    private String accessSecret;
//
//    @Value("${aws.region}")
//    private String region;
//
//
//    private S3Client s3Client() {
//        AwsCredentials credentials = new AwsBasicCredentials(accessKey, accessSecret);
//        S3Object s3Object = s3Client().getObject();
//        GetObjectR
//    }
}
