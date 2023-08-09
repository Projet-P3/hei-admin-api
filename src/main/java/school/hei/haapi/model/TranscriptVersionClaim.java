package school.hei.haapi.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.UpdateTimestamp;
import school.hei.haapi.endpoint.rest.model.TranscriptClaim;
import school.hei.haapi.repository.types.PostgresEnumType;

import javax.persistence.*;

import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "\"transcript_version_claim\"")
@Getter
@Setter
@TypeDef(name = "pgsql_enum", typeClass = PostgresEnumType.class)
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class TranscriptVersionClaim {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private String id;

    private String transcript_id;

    @ManyToOne
    @JoinColumn(name = "version_id", nullable = false)
    private TranscriptVersion version;

    @Type(type = "pgsql_enum")
    @Enumerated(EnumType.STRING)
    private TranscriptClaim.StatusEnum status;

    @CreationTimestamp
    private Instant creationDatetime;

    @UpdateTimestamp
    private Instant closedDatetime;

    private String reason;

}