package school.hei.haapi.model;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import school.hei.haapi.repository.types.PostgresEnumType;

import static javax.persistence.GenerationType.IDENTITY;
import static school.hei.haapi.endpoint.rest.model.Transcript.SemesterEnum;

@Entity
@Table(name = "\"transcript\"")
@TypeDef(name = "pgsql_enum", typeClass = PostgresEnumType.class)
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transcript {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  @NotBlank(message = "id is mandatory")
  private String id;
  @NotBlank(message = "studentId is mandatory")
  private String studentId;
  @Type(type = "pgsql_enum")
  @Enumerated(EnumType.STRING)
  @NotBlank(message = "semester is mandatory")
  private SemesterEnum semester;
  @NotBlank(message = "academicYear is mandatory")
  private int academicYear;
  @NotBlank(message = "isDefinitive is mandatory")
  private boolean isDefinitive;
  @CreationTimestamp
  @NotBlank(message = "creationDatetime is mandatory")
  private Instant creationDatetime;
}

