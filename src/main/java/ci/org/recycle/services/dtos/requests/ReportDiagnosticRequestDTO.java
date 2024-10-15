package ci.org.recycle.services.dtos.requests;

import java.util.UUID;

public record ReportDiagnosticRequestDTO(
        UUID decheId,
        UUID repairerId,
        float servicePrice
) {
}
