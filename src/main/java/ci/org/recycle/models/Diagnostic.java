package ci.org.recycle.models;

import ci.org.recycle.models.enumerations.StatusDiagnostic;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "diagnostique")
public class Diagnostic extends BaseEntityAudit {

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
