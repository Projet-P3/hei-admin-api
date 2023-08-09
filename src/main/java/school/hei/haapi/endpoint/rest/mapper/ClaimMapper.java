package school.hei.haapi.endpoint.rest.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.haapi.endpoint.rest.model.StudentTranscriptClaim;
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

    public StudentTranscriptClaim toRest(TranscriptVersionClaim domain, String transcriptId, String versionId, String studentId) {
        Transcript transcript = transcriptRepository.getById(transcriptId);
        if(domain.getVersion().getId() == versionId && transcript.getStudent().getId() == studentId){
            return new StudentTranscriptClaim()
                    .id(domain.getId())
                    .transcriptId(domain.getTranscript_id())
                    .transcriptVersionId(domain.getVersion().getId())
                    .status(domain.getStatus())
                    .creationDatetime(domain.getCreationDatetime())
                    .closedDatetime(domain.getClosedDatetime())
                    .reason(domain.getReason());
        }
        return new StudentTranscriptClaim();

    }
}
