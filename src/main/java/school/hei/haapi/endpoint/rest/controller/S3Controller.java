package school.hei.haapi.endpoint.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import school.hei.haapi.endpoint.rest.mapper.TranscriptVersionMapper;
import school.hei.haapi.endpoint.rest.model.TranscriptVersion;
import school.hei.haapi.service.S3Service;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class S3Controller {
    private final S3Service service;
    private TranscriptVersionMapper mapper;

    @PostMapping("/students/{student_id}/transcripts/{transcript_id}/versions/latest/raw")
    public TranscriptVersion putStudentTranscriptVersionPdf(
            @PathVariable(name = "student_id") String studentId,
            @PathVariable(name = "transcript_id") String transcriptId,
            @RequestParam(name = "pdf")MultipartFile transcript_pdf
            ) throws IOException {
        return mapper.toRest(service.uploadFile(transcript_pdf.getBytes(), transcriptId, studentId));
    }
}
