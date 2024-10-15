package ci.org.recycle.models;

import ci.org.recycle.models.enumerations.StatusRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "demande_collecte")
public class CollectionRequest extends BaseEntityAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Double latitude;
    private Double longitude;
    @Enumerated(EnumType.STRING)
    private StatusRequest statusRequest;

    @ManyToOne
    @JoinColumn(name = "deposit_id")
    private Deposit deposit;
}
