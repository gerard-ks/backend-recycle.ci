package ci.org.recycle.models;

import ci.org.recycle.models.enumerations.StatusRequest;
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
@Table(name = "demande_collecte")
public class CollectionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String collectionAddress;
    private String descriptionWaste;
    @Enumerated(EnumType.STRING)
    private StatusRequest statusRequest;

    @ManyToOne
    @JoinColumn(name = "deposit_id")
    private Deposit deposit;
}
