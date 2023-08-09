package school.hei.haapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import school.hei.haapi.model.Transcript;
import school.hei.haapi.model.User;

import java.util.List;

@Repository
public interface TranscriptRepository extends JpaRepository<Transcript, String> {
  Transcript findByIdAndStudent(String id, User student);

  List<Transcript> findTranscriptByStudentId(String studentId);
}
