package ci.org.recycle.models;

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
@Table(name = "point_collecte")
public class CollectionPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String description;
    private String localisation;
    private String isOpen;

    @OneToOne
    @JoinColumn(name = "collector_id", nullable = false)
    private Collector collector;
}
