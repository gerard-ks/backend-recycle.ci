package ci.org.recycle.services.dtos.responses;

import ci.org.recycle.models.enumerations.StatusDiagnostic;

import java.util.UUID;

public record DiagnosticResponseForRepairerDTO(
        UUID id,
        StatusDiagnostic statusDiagnostic,
        WasteResponseForRepairerDTO wasteResponseDTO
) {
}
