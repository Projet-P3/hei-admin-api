package school.hei.haapi.endpoint.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;
import school.hei.haapi.endpoint.rest.mapper.ClaimMapper;
import school.hei.haapi.endpoint.rest.model.StudentTranscriptClaim;
import school.hei.haapi.model.BoundedPageSize;
import school.hei.haapi.model.PageFromOne;
import school.hei.haapi.model.TranscriptVersion;
import school.hei.haapi.model.TranscriptVersionClaim;
import school.hei.haapi.model.exception.BadRequestException;
import school.hei.haapi.model.exception.NotFoundException;
import school.hei.haapi.model.validator.ClaimValidator;
import school.hei.haapi.repository.TranscriptVersionClaimRepository;
import school.hei.haapi.repository.TranscriptVersionRepository;
import school.hei.haapi.service.TranscriptVersionClaimService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class TranscriptVersionClaimController {

  private final TranscriptVersionClaimService service;
  private final ClaimValidator claimValidator;
  private final ClaimMapper mapper;
  private final TranscriptVersionRepository transcriptVersionRepository;
  @GetMapping("/students/{student_id}/transcripts/{transcript_id}/versions/{version_id}/claims")
  public List<StudentTranscriptClaim> getAllClaims (
    @PathVariable("student_id") String studentId,
    @PathVariable("transcript_id") String transcriptId,
    @PathVariable("version_id") String versionId,
    @RequestParam("page")PageFromOne page,
    @RequestParam("page_size") BoundedPageSize pageSize
    ){
     return service.getAllClaimTranscriptVersions(page, pageSize).stream()
       .map(claim -> mapper.toRest(claim, transcriptId, versionId, studentId))
       .collect(Collectors.toUnmodifiableList());
  }

  @GetMapping("/students/{student_id}/transcripts/{transcript_id}/versions/{version_id}/claims/{claim_id}")
  public StudentTranscriptClaim getClaimById(
    @PathVariable("student_id") String studentId,
    @PathVariable("transcript_id") String transcriptId,
    @PathVariable("version_id") String versionId,
    @PathVariable("claim_id") String claimId) {
    validateIds(transcriptVersionRepository.getById(versionId), transcriptId, versionId, studentId);
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
    validateIds(transcriptVersionRepository.getById(versionId), transcriptId, versionId, studentId);
    TranscriptVersionClaim updatedClaim = mapper.toDomain(toWrite, transcriptId, versionId, studentId, claimId);
    TranscriptVersionClaim savedClaim = service.updateClaimTranscriptVersion(updatedClaim);

    return mapper.toRest(savedClaim, transcriptId, versionId, studentId);
  }

  private void validateIds(TranscriptVersion version, String transcriptId, String versionId, String studentId) {
    if (!version.getId().equals(versionId)
      || !version.getTranscript().getId().equals(transcriptId)
      || !version.getTranscript().getStudent().getId().equals(studentId)) {
      throw new BadRequestException("IDs do not match the entity");
    }
  }
}
