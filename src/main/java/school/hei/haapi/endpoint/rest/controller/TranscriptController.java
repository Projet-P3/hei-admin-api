package school.hei.haapi.endpoint.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.haapi.endpoint.rest.mapper.TranscriptMapper;
import school.hei.haapi.endpoint.rest.model.Transcript;
import school.hei.haapi.service.TranscriptService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class TranscriptController {
    private final TranscriptService service;
    private final TranscriptMapper mapper;

    @GetMapping("/students/{student_id}/transcripts/{transcript_id}")
    public Transcript getStudentTranscriptById(
            @PathVariable String student_id,
            @PathVariable String transcript_id) {
        return mapper.toRest(
                service.getStudentTranscriptById(student_id, transcript_id)
        );
    }

    @GetMapping("/students/{student_id}/transcripts/{transcript_id}/versions/{version_id}/raw")
    public ResponseEntity<byte[]> downloadTranscriptRaw(
            @PathVariable("student_id") String studentId,
            @PathVariable("transcript_id") String transcriptId,
            @PathVariable("version_id") String versionId) throws IOException {
        try {
            String filename = "transcript" + versionId + ".pdf";
            File pdfFile = service.getTranscriptRaw(versionId);
            byte[] pdfBytes = Files.readAllBytes(pdfFile.toPath());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData(filename, filename);


            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);
        } catch (FileNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {

            return ResponseEntity.status(500).build();
        }
    }


    @PutMapping("/students/{id}/transcripts")
    public List<Transcript> crupdateTranscripts(@PathVariable("id") String studentId,
                                                @RequestBody List<Transcript> transcripts) {
        var toSave = transcripts.stream()
                .map(mapper::toDomain)
                .collect(Collectors.toUnmodifiableList());
        return service.crupdateTranscripts(toSave).stream()
                .map(mapper::toRest)
                .collect(Collectors.toList());
    }

    @GetMapping("/students/{student_id}/transcripts")
    public List<Transcript> getStudentTranscripts(@PathVariable("student_id") String studentId) {
        return service.getTranscriptsByStudentId(studentId).stream()
                .map(mapper::toRest)
                .collect(Collectors.toUnmodifiableList());
    }
}
