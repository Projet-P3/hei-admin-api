package school.hei.haapi.endpoint.rest.controller;

//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import com.amazonaws.services.s3.model.S3Object;
//import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class TranscriptController {
    private final TranscriptService service;
    private final TranscriptMapper mapper;

    @GetMapping("/students/{student_id}/transcripts")
    public List<Transcript> getStudentTranscripts(@PathVariable("student_id")String studentId) {
        return service.getTranscriptsByStudentId(studentId).stream()
                .map(mapper::toRest)
                .collect(Collectors.toUnmodifiableList());
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

}

