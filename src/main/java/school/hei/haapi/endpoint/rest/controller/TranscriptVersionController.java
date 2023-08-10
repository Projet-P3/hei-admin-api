package school.hei.haapi.endpoint.rest.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import school.hei.haapi.endpoint.rest.mapper.TranscriptVersionMapper;
import school.hei.haapi.endpoint.rest.model.StudentTranscriptVersion;
import school.hei.haapi.service.TranscriptVersionService;

@RestController
@RequiredArgsConstructor
public class TranscriptVersionController {
    private final TranscriptVersionService transcriptVersionService;
    private final TranscriptVersionMapper transcriptVersionMapper;

    @GetMapping("/students/{student_id}/transcripts/{transcript_id}/versions/{version_id}")
    public StudentTranscriptVersion getStudentTranscriptByVersion(
            @PathVariable("student_id") String student_id,
            @PathVariable("transcript_id") String transcript_id,
            @PathVariable("version_id") String version_id
    ) {
        return transcriptVersionMapper.toRest(
                transcriptVersionService.getStudentTranscriptByVersion(student_id, transcript_id, version_id)
        );
    }
}
