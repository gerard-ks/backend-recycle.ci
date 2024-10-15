package ci.org.recycle.repositories;

import ci.org.recycle.models.Citizen;
import ci.org.recycle.models.Picker;
import ci.org.recycle.models.Repairer;
import ci.org.recycle.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IPickerRepository extends JpaRepository<Picker, UUID> {
    Optional<Picker> findByUser(User user);

    @Query("SELECT r FROM Picker r WHERE " +
            "LOWER(r.user.firstName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(r.user.lastName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(r.serialNumber) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Picker> findBySearchQuery(@Param("query") String query, Pageable pageable);

    List<Picker> findByCollectionPoint_Id(UUID id);

    Optional<Picker> findByUserId(UUID id);
}
