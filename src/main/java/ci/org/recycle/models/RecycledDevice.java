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
@Table(name = "appareil_recycle")
public class RecycledDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "recycler_id")
    private Recycler recycler;

    @ManyToOne
    @JoinColumn(name = "collection_center_id")
    private CollectionCenter collectionCenter;
}
