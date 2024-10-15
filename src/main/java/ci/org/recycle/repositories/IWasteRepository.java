package ci.org.recycle.repositories;

import ci.org.recycle.models.Waste;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IWasteRepository extends JpaRepository<Waste, UUID> {

    @Query("SELECT w FROM Waste w JOIN w.deposit d WHERE d.citizen.id = :citizenId")
    Page<Waste> findWastesByCitizenId(@Param("citizenId") UUID citizenId, Pageable pageable);

    @Query("SELECT w FROM Waste w JOIN w.deposit d WHERE d.collectionPoint.id = :collectionPointId")
    Page<Waste> findWastesByCollectionPointId(@Param("collectionPointId") UUID collectionPointId, Pageable pageable);
}
