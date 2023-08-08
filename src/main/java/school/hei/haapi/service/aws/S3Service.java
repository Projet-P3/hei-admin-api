package school.hei.haapi.service.aws;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

@Service
@AllArgsConstructor
public class S3Service {

  private final S3Conf s3Conf;
  private final S3Client s3Client;

  public byte[] downloadPdfFromS3(String key){
    GetObjectRequest objectRequest;
    try{
      objectRequest = GetObjectRequest.builder()
        .bucket(s3Conf.getBucketName())
        .key(key)
        .build();
      return s3Client.getObjectAsBytes(objectRequest).asByteArray();
    } catch (NoSuchKeyException e){
      System.err.println("S3 file " + key + " not found");
      return null;
    }
  }

}
