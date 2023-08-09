package school.hei.haapi.service;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import school.hei.haapi.model.TranscriptVersion;
import school.hei.haapi.repository.TranscriptVersionRepository;
import school.hei.haapi.repository.UserRepository;
import software.amazon.awssdk.core.internal.waiters.ResponseOrException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.ChecksumAlgorithm;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@Service
@AllArgsConstructor
public class S3Service {
    private final S3conf s3conf;
    private final S3Client s3Client;
    private final TranscriptVersionRepository transcriptVersionRepository;
    private final UserRepository userRepository;

    public TranscriptVersion uploadFile(byte[] toUpload, String transcriptId, String studentId) {

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(s3conf.getBucketName())
                .contentType(MediaType.APPLICATION_PDF_VALUE)
                .checksumAlgorithm(ChecksumAlgorithm.SHA256)
                .key("tr_"+transcriptId+"!std_"+studentId)
                .build();

        PutObjectResponse objectResponse = s3conf.s3Client().putObject(request, RequestBody.fromBytes(toUpload));

        ResponseOrException<HeadObjectResponse> responseOrException = s3conf.s3Client()
                .waiter()
                .waitUntilObjectExists(
                        HeadObjectRequest.builder()
                                .bucket(s3conf.getBucketName())
                                .key("tr_"+transcriptId+"std_"+studentId)
                                .build())
                .matched();

        return transcriptVersionRepository.save(TranscriptVersion.builder()
                        .ref(transcriptVersionRepository.findAll().size())
                        .createdByUser(userRepository.findById(studentId).get())
                        .createdByUserRole(userRepository.findById(studentId).get().getRole().toString())
                .build());
    }

    public byte[] downloadPdfFromS3(String key){
        GetObjectRequest objectRequest;
        try{
            objectRequest = GetObjectRequest.builder()
                    .bucket(s3conf.getBucketName())
                    .key(key)
                    .build();
            return s3Client.getObjectAsBytes(objectRequest).asByteArray();
        } catch (NoSuchKeyException e){
            System.err.println("S3 file " + key + " not found");
            return null;
        }
    }
}
