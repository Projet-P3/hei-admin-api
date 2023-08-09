package school.hei.haapi.endpoint.rest.mapper;

import org.springframework.stereotype.Component;
import school.hei.haapi.model.TranscriptVersion;

@Component
public class TranscriptVersionMapper {
    public school.hei.haapi.endpoint.rest.model.TranscriptVersion toRest(TranscriptVersion version) {
        return new school.hei.haapi.endpoint.rest.model.TranscriptVersion()
                .transcriptId(version.getTranscript().getId())
                .id(version.getId())
                .ref(version.getRef())
                .creationDatetime(version.getCreationDatetime())
                .createdByUserId(version.getCreatedByUser().getId())
                .createdByUserRole(version.getCreatedByUserRole());
    }
}
