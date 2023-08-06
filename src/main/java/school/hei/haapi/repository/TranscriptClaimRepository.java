package school.hei.haapi.repository;

import school.hei.haapi.model.TranscriptVersionClaim;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TranscriptClaimRepository extends JpaRepository<TranscriptVersionClaim, String>{
}
