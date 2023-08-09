package school.hei.haapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.hei.haapi.model.Transcript;
import school.hei.haapi.model.TranscriptVersion;

import java.util.Optional;


@Repository
public interface TranscriptVersionRepository extends JpaRepository<TranscriptVersion, String> {

    Optional<TranscriptVersion> findByIdAndTranscriptId(String id, String transcriptId);

    TranscriptVersion findByIdAndTranscript(String id, Transcript transcript);

}
