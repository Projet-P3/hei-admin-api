package school.hei.haapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transcript {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private String id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @Type(type = "pgsql_enum")
    @Enumerated(EnumType.STRING)
    private school.hei.haapi.endpoint.rest.model.Transcript.SemesterEnum semester;

    @NotBlank(message = "academic year is mandatory")
    @Column(nullable = false)
    private int academicYear;

    private boolean isDefinitive;

    private Instant creationDatetime;

    public enum Semester {
        S1, S2, S3, S4, S5, S6
    }
}
