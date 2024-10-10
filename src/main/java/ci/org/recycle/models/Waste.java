package ci.org.recycle.models;


import ci.org.recycle.models.enumerations.ActionWaste;
import ci.org.recycle.models.enumerations.ConditionWaste;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "dechet")
public class Waste extends BaseEntityAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private float weight;
    @Enumerated(EnumType.STRING)
    private ConditionWaste condition;
    private String urlImage;
    private String details;
    @Enumerated(EnumType.STRING)
    private ActionWaste action;

    @ManyToOne
    @JoinColumn(name = "typewaste_id", nullable = false)
    private TypeWaste typeWaste;

    @ManyToOne
    @JoinColumn(name = "deposit_id", nullable = false)
    private Deposit deposit;

    @OneToOne
    @JoinColumn(name = "report_diagnostic_id")
    private ReportDiagnostic reportDiagnostic;
}
