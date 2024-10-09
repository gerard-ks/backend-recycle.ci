package ci.org.recycle.models;


import ci.org.recycle.models.enumerations.ActionWaste;
import ci.org.recycle.models.enumerations.ConditionWaste;
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
@Table(name = "dechet")
public class Waste {

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
