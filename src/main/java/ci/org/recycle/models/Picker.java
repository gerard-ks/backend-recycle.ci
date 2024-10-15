package ci.org.recycle.models;

import ci.org.recycle.models.enumerations.StatutAgent;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "agent_point_collecte")
public class Picker extends BaseEntityAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    private StatutAgent statut = StatutAgent.DISPONIBLE;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "collectionPoint_id", nullable = false)
    private CollectionPoint collectionPoint;
}
