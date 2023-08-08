package school.hei.haapi.endpoint.rest.controller;

import lombok.AllArgsConstructor;
import school.hei.haapi.endpoint.rest.mapper.ClaimMapper;
import school.hei.haapi.model.TranscriptVersionClaim;
import school.hei.haapi.service.TranscriptVersionClaimService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class TranscriptVersionClaimController {

    private final TranscriptVersionClaimService service;
    private final ClaimMapper mapper;

    @GetMapping("/students/{student_id}/transcripts/{transcript_id}/versions/{version_id}/claims")
    public List<TranscriptVersionClaim> getAllClaimTranscriptVersions(
            @PathVariable("student_id") String studentId,
            @PathVariable("transcript_id") String transcriptId,
            @PathVariable("version_id") String versionId) {
        return service.getAllClaimTranscriptVersions();
    }
}

