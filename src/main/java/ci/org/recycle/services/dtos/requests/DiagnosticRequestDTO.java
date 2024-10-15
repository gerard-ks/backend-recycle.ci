package ci.org.recycle.services.dtos.requests;

import java.util.UUID;

public record DiagnosticRequestDTO(
        UUID dechetId,
        UUID repairerId
) {
}
