package school.hei.haapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.hei.haapi.model.Transcript;
import school.hei.haapi.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface TranscriptRepository extends JpaRepository<Transcript, String> {

    Optional<Transcript> findByIdAndStudentId(String id, String studentId);

    Transcript findByIdAndStudent(String id, User student);

    List<Transcript> findTranscriptByStudentId(String studentId);
}
