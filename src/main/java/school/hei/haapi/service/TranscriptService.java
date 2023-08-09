package school.hei.haapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.haapi.model.Transcript;
import school.hei.haapi.model.exception.NotFoundException;
import school.hei.haapi.repository.TranscriptRepository;
import school.hei.haapi.repository.UserRepository;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TranscriptService {
    private final TranscriptRepository repository;
    private final S3Service s3Service;
    private final UserRepository userRepository;


    public List<Transcript> getTranscriptsByStudentId(String studentId) {
        return repository.findTranscriptByStudentId(studentId);
    }

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

    public Transcript getStudentTranscriptById(String studentId, String transcriptId) {
        Optional<Transcript> transcript = repository.findByIdAndStudentId(transcriptId, studentId);
        if(transcript.isPresent()) {
            return transcript.get();
        } else {
            throw new NotFoundException("Transcript not found");
        }
    }
}
