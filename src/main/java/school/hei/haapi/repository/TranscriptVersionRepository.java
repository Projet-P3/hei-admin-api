package school.hei.haapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import school.hei.haapi.model.TranscriptVersion;


@Repository
public interface TranscriptVersionRepository extends JpaRepository<TranscriptVersion, String> {
    @Query("SELECT tv FROM TranscriptVersion tv WHERE " +
            "tv.transcriptId.id = :transcriptId AND " +
            "tv.transcriptId.studentId.id = :studentId AND " +
            "tv.id = :versionId")
    TranscriptVersion getStudentTranscriptByVersion(
            @Param("studentId") String studentId,
            @Param("transcriptId") String transcriptId,
            @Param("versionId") String versionId
    );
}
