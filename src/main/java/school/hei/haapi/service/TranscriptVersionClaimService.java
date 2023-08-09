package school.hei.haapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.haapi.model.BoundedPageSize;
import school.hei.haapi.model.PageFromOne;
import school.hei.haapi.model.TranscriptVersionClaim;
import school.hei.haapi.repository.TranscriptVersionClaimRepository;
import school.hei.haapi.model.TranscriptVersionClaim;
import school.hei.haapi.repository.TranscriptVersionClaimRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class TranscriptVersionClaimService {
    private final TranscriptVersionClaimRepository repository;

    public List<TranscriptVersionClaim> getAllClaimTranscriptVersions(String student_id, String transcript_id , String version_id,PageFromOne page, BoundedPageSize pageSize) {
        return repository.findAll();
    }

    public TranscriptVersionClaim getClaimTranscriptVersion(String id){
        return repository.getById(id);
    }

    public TranscriptVersionClaim createClaimTranscriptVersion( TranscriptVersionClaim tocrupDate){
        return  repository.save(tocrupDate);
    }
}
