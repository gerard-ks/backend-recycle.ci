package ci.org.recycle.models;

import ci.org.recycle.models.enumerations.StatusDeposit;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "depot")
public class Deposit extends BaseEntityAudit  {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate depositDate;
    @Enumerated(EnumType.STRING)
    private StatusDeposit statusDeposit;

    @ManyToOne
    @JoinColumn(name = "citizen_id")
    private Citizen citizen;

    @ManyToOne
    @JoinColumn(name = "collection_point")
    private CollectionPoint collectionPoint;
}
