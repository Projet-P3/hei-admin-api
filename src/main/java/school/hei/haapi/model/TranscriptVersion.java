package school.hei.haapi.model;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
  private String id;
  private Transcript transcriptId;
  private int ref;
  private User createdByUserId;
  private String createdByUserRole;
  @CreationTimestamp
  private Instant creationDatetime;
}
