package ci.org.recycle.repositories;

import ci.org.recycle.models.Picker;
import ci.org.recycle.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IPickerRepository extends JpaRepository<Picker, UUID> {
    Optional<Picker> findByUser(User user);
}
