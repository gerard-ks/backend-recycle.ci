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
@Table(name = "reparateur")
public class Repairer extends BaseEntityAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String speciality;
    private Integer yearOfExperience;
    private String certificate;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
