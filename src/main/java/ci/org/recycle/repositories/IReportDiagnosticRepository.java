package ci.org.recycle.repositories;

import ci.org.recycle.models.ReportDiagnostic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IReportDiagnosticRepository extends JpaRepository<ReportDiagnostic, UUID> {
}
