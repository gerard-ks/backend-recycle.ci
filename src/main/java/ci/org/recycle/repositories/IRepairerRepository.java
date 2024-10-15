package ci.org.recycle.repositories;

import ci.org.recycle.models.Repairer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IRepairerRepository extends JpaRepository<Repairer, UUID> {
    @Query("SELECT r FROM Repairer r WHERE " +
            "LOWER(r.user.firstName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(r.user.lastName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(r.speciality) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Repairer> findBySearchQuery(@Param("query") String query, Pageable pageable);

    Optional<Repairer> findByUserId(UUID id);
}
