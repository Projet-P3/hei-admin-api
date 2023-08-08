package school.hei.haapi.repository;

import org.springframework.stereotype.Repository;
import school.hei.haapi.model.TranscriptVersionClaim;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TranscriptVersionClaimRepository extends JpaRepository<TranscriptVersionClaim, String>{
}
