package school.hei.haapi.endpoint.rest.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import school.hei.haapi.service.TranscriptVersionService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@AllArgsConstructor
public class TransciptVersionController {

  private TranscriptVersionService service;

  @GetMapping("/students/{studentId}/transcripts/{transcriptId}/versions/{versionId}/raw")
  public byte[] downloadTranscriptRaw(
    @PathVariable("studentId") String studentId,
    @PathVariable("transcriptId") String transcriptId,
    @PathVariable("versionId") String versionId) throws IOException {

    return service.getTranscriptRaw(studentId, transcriptId, versionId);

  }

}
