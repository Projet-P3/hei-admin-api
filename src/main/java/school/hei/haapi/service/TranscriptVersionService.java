package school.hei.haapi.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.haapi.model.TranscriptVersion;
import school.hei.haapi.model.User;
import school.hei.haapi.model.exception.NotFoundException;
import school.hei.haapi.repository.TranscriptRepository;
import school.hei.haapi.repository.TranscriptVersionRepository;
import school.hei.haapi.repository.UserRepository;

@Service
@AllArgsConstructor
public class TranscriptVersionService {

  private TranscriptVersionRepository repository;
  private UserRepository userRepository;
  private TranscriptRepository transcriptRepository;
  private S3Service service;
    public byte[] getTranscriptRaw(String studentId, String transcriptId, String versionId) throws NotFoundException {
    String keyName = studentId+transcriptId;

    return service.downloadPdfFromS3(keyName);
  }
    private final TranscriptVersionRepository transcriptVersionRepository;

    public TranscriptVersion getStudentTranscriptByVersion(
            String studentId,
            String transcriptId,
            String versionId
    ) {
        return transcriptVersionRepository.getStudentTranscriptByVersionId(transcriptId, studentId, versionId);
    }
}

