package school.hei.haapi.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.haapi.model.TranscriptVersion;
import school.hei.haapi.model.User;
import school.hei.haapi.model.exception.NotFoundException;
import school.hei.haapi.repository.TranscriptRepository;
import school.hei.haapi.repository.TranscriptVersionRepository;
import school.hei.haapi.repository.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TranscriptVersionService {

    private final TranscriptVersionRepository transcriptVersionRepository;
    private TranscriptVersionRepository repository;
    private UserRepository userRepository;
    private TranscriptRepository transcriptRepository;
    private S3Service service;

    public byte[] getTranscriptRaw(String studentId, String transcriptId, String versionId) throws NotFoundException {
        User student = userRepository.findById(studentId).orElseThrow(
                () -> new NotFoundException("user not found")
        );

        String keyName = "tr_" + transcriptId + "std_" + studentId;

        return service.gdownloadPdfFromS3(keyName);
    }

    public TranscriptVersion getStudentTranscriptByVersion(
            String studentId,
            String transcriptId,
            String versionId
    ) {
        Optional<TranscriptVersion> transcriptVersion = repository.findByIdAndTranscriptId(versionId, transcriptId);
        if (transcriptVersion.isPresent()) {
            return transcriptVersion.get();
        } else {
            throw new NotFoundException("version not found");
        }
    }
}
