package ci.org.recycle.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "rapport_diagnostique")
public class ReportDiagnostic extends BaseEntityAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private float servicePrice;
    private String urlDocument;

    @OneToOne
    @JoinColumn(name = "repairer_id", nullable = false)
    private Repairer repairer;
}
