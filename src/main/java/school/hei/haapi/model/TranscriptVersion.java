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

@Entity
@Table(name = "\"transcript-version\"")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TranscriptVersion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NotBlank(message = "id is mandatory")
  private String id;
  @ManyToOne()
  @JoinColumn(name = "transcript_id", referencedColumnName = "id")
  @NotBlank(message = "transcriptId is mandatory")
  private Transcript transcriptId;
  @NotBlank(message = "ref is mandatory")
  private int ref;
  @ManyToOne()
  @JoinColumn(name = "created_by_user_id", referencedColumnName = "id")
  @NotBlank(message = "created_by_user_id is mandatory")
  private User createdByUserId;
  @NotBlank(message = "created_by_user_role is mandatory")
  private String createdByUserRole;
  @CreationTimestamp
  @NotBlank(message = "creation_datetime is mandatory")
  private Instant creationDatetime;
}
