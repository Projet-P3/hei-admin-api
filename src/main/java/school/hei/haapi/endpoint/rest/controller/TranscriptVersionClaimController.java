package school.hei.haapi.endpoint.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import school.hei.haapi.endpoint.rest.mapper.ClaimMapper;
import school.hei.haapi.endpoint.rest.model.StudentTranscriptClaim;
import school.hei.haapi.model.BoundedPageSize;
import school.hei.haapi.model.PageFromOne;
import school.hei.haapi.model.TranscriptVersionClaim;
import school.hei.haapi.service.TranscriptVersionClaimService;

import java.util.List;

@RestController
@AllArgsConstructor
public class TranscriptVersionClaimController {

    private final TranscriptVersionClaimService service;
    private final ClaimMapper mapper;

    @GetMapping("/students/{student_id}/transcripts/{transcript_id}/versions/{version_id}/claims")
    public List<TranscriptVersionClaim> getAllClaimTranscriptVersions(
            @RequestParam PageFromOne page,
            @RequestParam("page_size") BoundedPageSize pageSize,
            @RequestParam(required = false)
            @PathVariable("student_id") String student_id,
            @PathVariable("transcript_id") String transcript_id,
            @PathVariable("version_id") String version_id) {
        return service.getAllClaimTranscriptVersions(student_id, transcript_id, version_id, page, pageSize);
    }
    @GetMapping("/students/{student_id}/transcripts/{transcript_id}/versions/{version_id}/claims/{claim_id}")
    public StudentTranscriptClaim getClaimById(
            @PathVariable("student_id") String student_id,
            @PathVariable("transcript_id") String transcript_id,
            @PathVariable("version_id") String version_id,
            @PathVariable("claim_id") String claim_id) {
        return mapper.toRest(service.getClaimTranscriptVersion(claim_id), transcript_id, version_id, student_id);
    }

    @PutMapping("/students/{student_id}/transcripts/{transcript_id}/versions/{version_id}/claims/{claim_id}")
    public TranscriptVersionClaim crupDateClaimById(
            @PathVariable("student_id") String student_id,
            @PathVariable("transcript_id") String transcript_id,
            @PathVariable("version_id") String version_id,
            @PathVariable("claim_id") String claim_id,
            @RequestBody StudentTranscriptClaim toWrite) {

        return mapper.toDomain(toWrite, transcript_id, version_id, student_id);

    }
}

