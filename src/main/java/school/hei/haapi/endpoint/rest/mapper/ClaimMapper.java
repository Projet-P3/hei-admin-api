package school.hei.haapi.endpoint.rest.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.haapi.endpoint.rest.model.StudentTranscriptClaim;
import school.hei.haapi.model.Transcript;
import school.hei.haapi.model.TranscriptVersion;
import school.hei.haapi.model.TranscriptVersionClaim;
import school.hei.haapi.model.exception.BadRequestException;
import school.hei.haapi.model.exception.ForbiddenException;
import school.hei.haapi.model.validator.ClaimValidator;
import school.hei.haapi.repository.TranscriptRepository;
import school.hei.haapi.repository.TranscriptVersionClaimRepository;
import school.hei.haapi.repository.TranscriptVersionRepository;
import school.hei.haapi.service.TranscriptVersionClaimService;

@Component
@AllArgsConstructor
public class ClaimMapper {
  private final TranscriptVersionClaimRepository claimRepository;
  private final TranscriptVersionRepository transcriptVersionRepository;

  public StudentTranscriptClaim toRest(TranscriptVersionClaim domain, String transcriptId, String versionId, String studentId) {


    return new StudentTranscriptClaim()
      .id(domain.getId())
      .transcriptId(domain.getTranscript_id())
      .transcriptVersionId(domain.getVersion().getId())
      .status(domain.getStatus())
      .creationDatetime(domain.getCreationDatetime())
      .closedDatetime(domain.getClosedDatetime())
      .reason(domain.getReason());
  }

  public TranscriptVersionClaim toDomain(TranscriptVersionClaim restClaim, String transcriptId, String versionId, String studentId, String claimID) {

    TranscriptVersion version = transcriptVersionRepository.getById(versionId);
    TranscriptVersionClaim claim = new TranscriptVersionClaim()
      .toBuilder()
      .id(claimID)
      .version(version)
      .status(restClaim.getStatus())
      .creationDatetime(restClaim.getCreationDatetime())
      .closedDatetime(restClaim.getClosedDatetime())
      .reason(restClaim.getReason())
      .build();

    return claim;
  }

}
