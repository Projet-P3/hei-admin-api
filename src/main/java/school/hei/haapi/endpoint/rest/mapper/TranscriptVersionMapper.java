package school.hei.haapi.endpoint.rest.mapper;

import org.springframework.stereotype.Component;
import school.hei.haapi.endpoint.rest.model.StudentTranscriptVersion;
import school.hei.haapi.model.TranscriptVersion;

@Component
public class TranscriptVersionMapper {
    public StudentTranscriptVersion toRest(TranscriptVersion version) {
        return new StudentTranscriptVersion()
                .transcriptId(version.getTranscript().getId())
                .id(version.getId())
                .ref(version.getRef())
                .creationDatetime(version.getCreationDateTime())
                .createdByUserId(version.getCreatedByUser().getId())
                .createdByUserRole(version.getCreatedByUserRole());
    }
}
