package school.hei.haapi.model;

import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import school.hei.haapi.endpoint.rest.model.StudentTranscriptClaim;
import school.hei.haapi.repository.types.PostgresEnumType;

import javax.persistence.*;

import java.time.Instant;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "\"transcript_version_claim\"")
@Getter
@Setter
@TypeDef(name = "pgsql_enum", typeClass = PostgresEnumType.class)
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TranscriptVersionClaim {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private String id;

    @ManyToOne
    @JoinColumn(name = "transcript_id", nullable = false)
    private Transcript transcript;

    @ManyToOne
    @JoinColumn(name = "version_id", nullable = false)
    private TranscriptVersion version;

    @Type(type = "pgsql_enum")
    @Enumerated(EnumType.STRING)
    private StudentTranscriptClaim.StatusEnum status;

    private Instant creationDateTime;

    private Instant closedDateTime;

    private String reason;

}