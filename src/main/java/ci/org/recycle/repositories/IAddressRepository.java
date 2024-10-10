package ci.org.recycle.repositories;

import ci.org.recycle.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IAddressRepository extends JpaRepository<Address, UUID> {
}
