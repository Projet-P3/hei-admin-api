package school.hei.haapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import school.hei.haapi.model.TranscriptVersion;

import java.util.Optional;


@Repository
public interface TranscriptVersionRepository extends JpaRepository<TranscriptVersion, String> {
    Optional<TranscriptVersion> findByIdAndTranscriptId(String id, String transcriptId);
}
