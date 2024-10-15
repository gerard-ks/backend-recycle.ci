package ci.org.recycle.repositories;

import ci.org.recycle.models.Diagnostic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IDiagnosticRepository extends JpaRepository<Diagnostic, UUID> {

    @Query("SELECT d FROM Diagnostic d WHERE d.repairer.id = :repairerId")
    Page<Diagnostic> findWastesByRepairerId(@Param("repairerId") UUID repairerId, Pageable pageable);

}
