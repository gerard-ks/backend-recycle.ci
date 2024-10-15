package ci.org.recycle.repositories;

import ci.org.recycle.models.CollectionPoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ICollectionPointRepository extends JpaRepository<CollectionPoint, UUID> {


    @Query("SELECT cp FROM CollectionPoint cp " +
            "ORDER BY (6371 * acos(cos(radians(:latitude)) * cos(radians(cp.latitude)) * " +
            "cos(radians(cp.longitude) - radians(:longitude)) + sin(radians(:latitude)) * " +
            "sin(radians(cp.latitude))))")
    Page<CollectionPoint> findNearestCollectionPoints(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            Pageable pageable);
}
