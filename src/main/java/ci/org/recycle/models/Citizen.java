package ci.org.recycle.models;

import ci.org.recycle.models.enumerations.CitizenType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "citoyen")
public class Citizen  {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Long loyaltyPoint;

    @Enumerated(EnumType.STRING)
    private CitizenType citizenType;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
