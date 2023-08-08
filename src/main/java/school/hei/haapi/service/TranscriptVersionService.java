package school.hei.haapi.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.haapi.model.Transcript;
import school.hei.haapi.model.TranscriptVersion;
import school.hei.haapi.model.User;
import school.hei.haapi.model.exception.NotFoundException;
import school.hei.haapi.repository.TranscriptRepository;
import school.hei.haapi.repository.TranscriptVersionRepository;
import school.hei.haapi.repository.UserRepository;
import school.hei.haapi.service.aws.S3Service;
import school.hei.haapi.service.utils.FilePdfUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
@AllArgsConstructor
public class TranscriptVersionService {

  private TranscriptVersionRepository repository;
  private UserRepository userRepository;
  private TranscriptRepository transcriptRepository;
  private S3Service service;
    public byte[] getTranscriptRaw(String studentId, String transcriptId, String versionId)  {

    User student = userRepository.findById(studentId).get();
    Transcript transcript = transcriptRepository.findById(transcriptId).get();
    TranscriptVersion version = repository.findById(versionId).get();

    String keyName = student.getRef()+"_"+transcript.getSemester()+"_v"+version.getRef();

    return service.downloadPdfFromS3(keyName);
  }
}
