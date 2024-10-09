package ci.org.recycle.models;

import ci.org.recycle.models.enumerations.StatusDeposit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "depot")
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate depositDate;
    private float totalWeight;
    @Enumerated(EnumType.STRING)
    private StatusDeposit statusDeposit;

    @ManyToOne
    @JoinColumn(name = "citizen_id")
    private Citizen citizen;

    @ManyToOne
    @JoinColumn(name = "collection_point")
    private CollectionPoint collectionPoint;
}
