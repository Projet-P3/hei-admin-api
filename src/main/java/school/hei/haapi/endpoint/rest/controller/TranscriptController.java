package school.hei.haapi.endpoint.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import school.hei.haapi.endpoint.rest.mapper.TranscriptMapper;
import school.hei.haapi.endpoint.rest.mapper.TranscriptVersionMapper;
import school.hei.haapi.endpoint.rest.model.StudentTranscriptVersion;
import school.hei.haapi.endpoint.rest.model.Transcript;
import school.hei.haapi.endpoint.rest.security.AuthProvider;
import school.hei.haapi.model.User;
import school.hei.haapi.service.S3Service;
import school.hei.haapi.service.TranscriptService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class TranscriptController {
    private final TranscriptService service;
    private final TranscriptMapper mapper;
    private final S3Service s3Service;
    private final TranscriptVersionMapper transcriptVersionMapper;
    private TranscriptVersionMapper versionMapper;

    @GetMapping("/students/{student_id}/transcripts/{transcript_id}")
    public Transcript getStudentTranscriptById(
            @PathVariable String student_id,
            @PathVariable String transcript_id) {
        return mapper.toRest(
                service.getStudentTranscriptById(student_id, transcript_id)
        );
    }


    @PostMapping("/students/{student_id}/transcripts/{transcript_id}/versions/latest/raw")
    public StudentTranscriptVersion putStudentTranscriptVersionPdf(
            @PathVariable(name = "student_id") String studentId,
            @PathVariable(name = "transcript_id") String transcriptId,
            @RequestParam(name = "pdf") MultipartFile transcript_pdf
    ) throws IOException {
        User user_connected = AuthProvider.getPrincipal().getUser();
        return transcriptVersionMapper.toRest(s3Service.uploadFile(transcript_pdf.getBytes(), transcriptId, studentId, user_connected));
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
