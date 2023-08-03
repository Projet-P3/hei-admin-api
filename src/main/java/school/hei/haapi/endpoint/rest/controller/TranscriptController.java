package school.hei.haapi.endpoint.rest.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import school.hei.haapi.endpoint.rest.mapper.TranscriptMapper;
import school.hei.haapi.endpoint.rest.model.Transcript;
import school.hei.haapi.service.TranscriptService;

@RestController
@AllArgsConstructor
public class TranscriptController {
  private final TranscriptService transcriptService;
  private final TranscriptMapper transcriptMapper;

  @GetMapping("/students/{student_id}/transcripts/{transcript_id}")
  public Transcript getStudentTranscriptById(
    @PathVariable String student_id,
    @PathVariable String transcript_id)
  {
    return transcriptMapper.toRest(
      transcriptService.getStudentTranscriptById(student_id, transcript_id)
    );
  }
}
