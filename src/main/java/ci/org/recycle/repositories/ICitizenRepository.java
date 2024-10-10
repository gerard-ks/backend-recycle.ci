package ci.org.recycle.repositories;

import ci.org.recycle.models.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ICitizenRepository extends JpaRepository<Citizen, UUID> {
}
