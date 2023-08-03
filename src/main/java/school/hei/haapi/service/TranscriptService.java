package school.hei.haapi.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.haapi.model.Transcript;
import school.hei.haapi.repository.TranscriptRepository;

@Service
@AllArgsConstructor
public class TranscriptService {

  private final TranscriptRepository transcriptRepository;

  public Transcript getTranscriptById(String id) {
    return transcriptRepository.getById(id);
  }

  public Transcript getStudentTranscriptById(String studentId, String transcriptId) {
    return transcriptRepository.findByTranscriptIdAndStudentId(studentId, transcriptId);
  }
}
