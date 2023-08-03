package school.hei.haapi.endpoint.rest.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.haapi.endpoint.rest.model.Transcript;
import school.hei.haapi.service.UserService;

@Component
@AllArgsConstructor
public class TranscriptMapper {

  private final UserService userService;
  public Transcript toRest(school.hei.haapi.model.Transcript domain) {
    return new Transcript()
      .id(domain.getId())
      .studentId(domain.getStudentId().getId())
      .semester(domain.getSemester())
      .academicYear(domain.getAcademicYear())
      .isDefinitive(domain.isDefinitive())
      .creationDatetime(domain.getCreationDatetime());
  }

  public school.hei.haapi.model.Transcript toDomain(Transcript rest) {
    return school.hei.haapi.model.Transcript.builder()
      .id(rest.getId())
      .studentId(userService.getById(rest.getId()))
      .semester(rest.getSemester())
      .academicYear(rest.getAcademicYear())
      .isDefinitive(rest.getIsDefinitive())
      .creationDatetime(rest.getCreationDatetime())
      .build();
  }
}
