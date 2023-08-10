package school.hei.haapi.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import school.hei.haapi.model.BoundedPageSize;
import school.hei.haapi.model.PageFromOne;
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
    public List<TranscriptVersionClaim> getAllClaimTranscriptVersions(PageFromOne page, BoundedPageSize pageSize) {
        int pageValue = page != null ? page.getValue()-1 : 0;
        int pageSizeValue = pageSize != null ? pageSize.getValue() : 10;
        Pageable pageable = PageRequest.of(pageValue, pageSizeValue);
        return repository.findAll(pageable).toList();
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
