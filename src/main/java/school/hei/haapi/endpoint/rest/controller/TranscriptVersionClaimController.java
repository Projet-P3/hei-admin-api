package school.hei.haapi.endpoint.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import school.hei.haapi.endpoint.rest.mapper.ClaimMapper;
import school.hei.haapi.endpoint.rest.model.StudentTranscriptClaim;
import school.hei.haapi.model.TranscriptVersionClaim;
import school.hei.haapi.model.exception.NotFoundException;
import school.hei.haapi.model.validator.ClaimValidator;
import school.hei.haapi.repository.TranscriptVersionClaimRepository;
import school.hei.haapi.service.TranscriptVersionClaimService;

@RestController
@AllArgsConstructor
public class TranscriptVersionClaimController {

  private final TranscriptVersionClaimService service;
  private final ClaimValidator claimValidator;
  private final ClaimMapper mapper;

  @GetMapping("/students/{student_id}/transcripts/{transcript_id}/versions/{version_id}/claims/{claim_id}")
  public StudentTranscriptClaim getClaimById(
    @PathVariable("student_id") String studentId,
    @PathVariable("transcript_id") String transcriptId,
    @PathVariable("version_id") String versionId,
    @PathVariable("claim_id") String claimId) {
    TranscriptVersionClaim claim = service.getClaimTranscriptVersion(claimId, studentId, transcriptId, versionId);
    return mapper.toRest(claim, transcriptId, versionId, studentId);
  }

  @PutMapping("/students/{student_id}/transcripts/{transcript_id}/versions/{version_id}/claims/{claim_id}")
  public StudentTranscriptClaim updateClaimById(
    @PathVariable("student_id") String studentId,
    @PathVariable("transcript_id") String transcriptId,
    @PathVariable("version_id") String versionId,
    @PathVariable("claim_id") String claimId,
    @RequestBody TranscriptVersionClaim toWrite) {

    claimValidator.validateIdsMatch(toWrite, studentId, transcriptId, versionId, claimId);

    TranscriptVersionClaim updatedClaim = mapper.toDomain(toWrite, transcriptId, versionId, studentId, claimId);
    TranscriptVersionClaim savedClaim = service.updateClaimTranscriptVersion(updatedClaim);

    return mapper.toRest(savedClaim, transcriptId, versionId, studentId);
  }
}
