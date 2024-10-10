package ci.org.recycle.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "horaire")
public class Schedule extends BaseEntityAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String day;
    private String starTime;
    private String endTime;

    @ManyToOne
    @JoinColumn(name = "collection_point_id")
    private CollectionPoint collectionPoint;
}
