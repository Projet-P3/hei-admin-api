package school.hei.haapi.endpoint.rest.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.haapi.endpoint.rest.model.TranscriptClaim;
import school.hei.haapi.model.Transcript;
import school.hei.haapi.model.TranscriptVersion;
import school.hei.haapi.model.TranscriptVersionClaim;
import school.hei.haapi.repository.TranscriptRepository;
import school.hei.haapi.repository.TranscriptVersionRepository;
import school.hei.haapi.service.TranscriptVersionClaimService;

@Component
@AllArgsConstructor
public class ClaimMapper {
  private final TranscriptRepository transcriptRepository;
  private final TranscriptVersionRepository transcriptVersionRepository;
  private final TranscriptVersionClaimService transcriptVersionClaimService;

  public TranscriptClaim toRest(TranscriptVersionClaim domain, String transcriptId, String versionId, String studentId) {
    Transcript transcript = transcriptRepository.getById(transcriptId);
    if(domain.getVersion().getId() == versionId && transcript.getStudent().getId() == studentId){
      return new TranscriptClaim()
        .id(domain.getId())
        .transcriptId(domain.getTranscript_id())
        .transcriptVersionId(domain.getVersion().getId())
        .status(domain.getStatus())
        .creationDatetime(domain.getCreationDatetime())
        .closedDatetime(domain.getClosedDatetime())
        .reason(domain.getReason());}
        return new TranscriptClaim();
    }

  public TranscriptVersionClaim toDomain(TranscriptClaim restClaim, String transcriptId, String versionId, String studentId) {

    Transcript transcript = transcriptRepository.getById(transcriptId);
    TranscriptVersion version = transcriptVersionRepository.getById(versionId);
    if (restClaim.getTranscriptVersionId() == versionId && transcript.getStudent().getId() == studentId) {
      TranscriptVersionClaim claim = new TranscriptVersionClaim()
        .toBuilder()
        .id(restClaim.getId())
        .version(version)
        .status(restClaim.getStatus())
        .creationDatetime(restClaim.getCreationDatetime())
        .closedDatetime(restClaim.getClosedDatetime())
        .reason(restClaim.getReason())
        .build();

      return transcriptVersionClaimService.createClaimTranscriptVersion(claim);
    }
    return new TranscriptVersionClaim();
    }
}
