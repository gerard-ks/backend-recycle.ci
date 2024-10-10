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
@Table(name = "agent_point_collecte")
public class Picker extends BaseEntityAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String serialNumber;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
