package ci.org.recycle.models;

import ci.org.recycle.models.enumerations.StatusDiagnostic;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "diagnostique")
public class Diagnostic {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private StatusDiagnostic statusDiagnostic;

    @OneToOne
    @JoinColumn(name = "waste_id")
    private Waste waste;

    @OneToOne
    @JoinColumn(name = "repairer_id")
    private Repairer repairer;
}
