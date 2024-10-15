package ci.org.recycle.repositories;

import ci.org.recycle.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IScheduleRepository extends JpaRepository<Schedule, UUID> {
    List<Schedule> findByCollectionPoint_Id(UUID collectionPointId);
}
