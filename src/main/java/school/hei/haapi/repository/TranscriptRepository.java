package school.hei.haapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import school.hei.haapi.model.Transcript;

import java.util.List;

@Repository
public interface TranscriptRepository extends JpaRepository<Transcript, String> {
  @Query(value = "SELECT t FROM Transcript t WHERE t.id = :transcriptId " +
          "AND t.studentId.id = :studentId")
  Transcript findByTranscriptIdAndStudentId(
          @Param("transcriptId") String transcriptId,
          @Param("studentId") String studentId
  );

  List<Transcript> findTranscriptByStudentId(String studentId);
}
