package school.hei.haapi.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import school.hei.haapi.model.TranscriptVersionClaim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface TranscriptVersionClaimRepository extends JpaRepository<TranscriptVersionClaim, String>{
}
