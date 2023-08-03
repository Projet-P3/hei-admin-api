package school.hei.haapi.model;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.TypeDef;
import school.hei.haapi.repository.types.PostgresEnumType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "\"transcript_version\"")
@Getter
@Setter
@TypeDef(name = "pgsql_enum", typeClass = PostgresEnumType.class)
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TranscriptVersion implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @NotBlank(message = "id is mandatory")
  private String id;

  @ManyToOne
  @JoinColumn(name = "transcript_id",nullable = false)
  private Transcript transcript;

  private int ref;
  private String createdByUserId;
  private String createdByUserRole;

  @CreationTimestamp
  @NotBlank(message = "creation_datetime is mandatory")
  private Instant creationDatetime;

}
