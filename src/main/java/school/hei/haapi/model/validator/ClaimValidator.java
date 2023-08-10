package school.hei.haapi.model.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.haapi.model.TranscriptVersionClaim;
import school.hei.haapi.model.exception.BadRequestException;
import school.hei.haapi.model.exception.ForbiddenException;
import school.hei.haapi.model.exception.NotFoundException;
import school.hei.haapi.repository.TranscriptVersionClaimRepository;
import school.hei.haapi.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
public class ClaimValidator implements Consumer<TranscriptVersionClaim> {
  @Override
  public void accept(TranscriptVersionClaim claim) {
    Set<String> violationMessages = new HashSet<>();

    if (claim.getVersion() == null) {
      violationMessages.add("Version is mandatory");
    }
    if (claim.getVersion().getTranscript() == null || claim.getVersion().getTranscript().getStudent() == null) {
      violationMessages.add("Student is mandatory");
    }
    if (claim.getCreationDatetime() == null) {
      violationMessages.add("Creation datetime is mandatory");
    }
    if (claim.getStatus() == null) {
      violationMessages.add("Status is mandatory");
    }
    if (claim.getReason() == null || claim.getReason().isEmpty()) {
      violationMessages.add("Reason is mandatory");
    }
    if (claim.getClosedDatetime() == null) {
      violationMessages.add("Closed datetime is mandatory");
    }

    if (claim.getId() == null || claim.getId().isEmpty()) {
      violationMessages.add("Claim ID is mandatory");
    }
    if (claim.getTranscript_id() == null || claim.getTranscript_id().isEmpty()) {
      violationMessages.add("Transcript ID is mandatory");
    }
    if (claim.getVersion().getId() == null || claim.getVersion().getId().isEmpty()) {
      violationMessages.add("Version ID is mandatory");
    }


    if (!violationMessages.isEmpty()) {
      String formattedViolationMessages = violationMessages.stream()
        .map(String::toString)
        .collect(Collectors.joining(". "));
      throw new BadRequestException(formattedViolationMessages);
    }
  }



  public void validateIdsMatch(TranscriptVersionClaim claim, String studentId, String transcriptId, String versionId, String claimId) {
    if (!claim.getId().equals(claimId)
      || !claim.getTranscript_id().equals(transcriptId)
      || !claim.getVersion().getId().equals(versionId)
      || !claim.getVersion().getTranscript().getStudent().getId().equals(studentId)) {
      throw new BadRequestException("Claim IDs do not match the path");
    }
  }
}

