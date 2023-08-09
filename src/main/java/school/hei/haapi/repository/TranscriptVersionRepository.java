package school.hei.haapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import school.hei.haapi.model.Transcript;
import school.hei.haapi.model.TranscriptVersion;


@Repository
public interface TranscriptVersionRepository extends JpaRepository<TranscriptVersion, String> {
    @Query("SELECT tv FROM TranscriptVersion tv " +
            "JOIN tv.transcript t " +
            "JOIN t.student s " +
            "WHERE t.id = :transcriptId " +
            "AND s.id = :studentId " +
            "AND tv.id = :versionId")
    TranscriptVersion getStudentTranscriptByVersionId(
            @Param("transcriptId") String transcriptId,
            @Param("studentId") String studentId,
            @Param("versionId") String versionId
    );

    TranscriptVersion findByIdAndTranscript(String id, Transcript transcript);


    
    
}
