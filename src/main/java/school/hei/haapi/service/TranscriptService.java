package school.hei.haapi.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.haapi.model.Transcript;
import school.hei.haapi.repository.TranscriptRepository;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@AllArgsConstructor
public class TranscriptService {

    private TranscriptRepository repository;

    public File getTranscriptRaw(String versionId) throws IOException {
        String bucketName = "transcript";
        String keyName = "transcript" + versionId;

        S3Client s3Client = S3Client.builder().build();

        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();

        ResponseInputStream<GetObjectResponse> inputStream = s3Client.getObject(request);

        Path tempFilePath = Files.createTempFile("transcript", ".pdf");
        File tempFile = tempFilePath.toFile();

        Files.copy(inputStream, tempFilePath);

        return tempFile;
    }

  public List<Transcript> crupdateTranscripts(List<Transcript> transcripts) {
    return repository.saveAll(transcripts);
  }

}
