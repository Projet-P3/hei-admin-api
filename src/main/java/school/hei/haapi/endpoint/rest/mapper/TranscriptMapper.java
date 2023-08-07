package school.hei.haapi.endpoint.rest.mapper;

import org.springframework.stereotype.Component;
import school.hei.haapi.endpoint.rest.model.Transcript;

@Component
public class TranscriptMapper {
    public Transcript toRest(school.hei.haapi.model.Transcript transcript) {
        Transcript restResponse = new Transcript();
        restResponse.setId(transcript.getId());
        restResponse.setAcademicYear(transcript.getAcademicYear());
        restResponse.isDefinitive(transcript.isDefinitive());
        restResponse.setCreationDatetime(transcript.getCreationDatetime());
        restResponse.semester(Transcript.SemesterEnum.fromValue(transcript.getSemester().toString()));
        restResponse.setStudentId(transcript.getStudent().getId());

        return restResponse;
    }
}
