package school.hei.haapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.haapi.model.TranscriptVersionClaim;
import school.hei.haapi.model.exception.NotFoundException;
import school.hei.haapi.model.validator.ClaimValidator;
import school.hei.haapi.repository.TranscriptVersionClaimRepository;
import school.hei.haapi.model.TranscriptVersionClaim;
import school.hei.haapi.repository.TranscriptVersionClaimRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class TranscriptVersionClaimService {
    private final TranscriptVersionClaimRepository repository;
    private final ClaimValidator claimValidator;
    public List<TranscriptVersionClaim> getAllClaimTranscriptVersions() {
        return repository.findAll();
    }

    public TranscriptVersionClaim getClaimTranscriptVersion(String claimId, String studentId, String transcriptId, String versionId) {
        TranscriptVersionClaim claim = repository.findById(claimId)
          .orElseThrow(() -> new NotFoundException("Claim not found"));

        claimValidator.validateIdsMatch(claim, studentId, transcriptId, versionId, claimId);

        return claim;
    }

    public TranscriptVersionClaim updateClaimTranscriptVersion(TranscriptVersionClaim claim) {
        claimValidator.validateIdsMatch(claim, claim.getVersion().getTranscript().getStudent().getId(),
          claim.getVersion().getTranscript().getId(), claim.getVersion().getId(), claim.getId());

        return repository.save(claim);
    }
}
