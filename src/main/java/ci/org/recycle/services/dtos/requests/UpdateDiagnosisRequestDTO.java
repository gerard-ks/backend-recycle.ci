package ci.org.recycle.services.dtos.requests;

import ci.org.recycle.models.enumerations.StatusDiagnostic;

import java.util.UUID;

public record UpdateDiagnosisRequestDTO(
        UUID diagnosisId,
        StatusDiagnostic statusDiagnostic
) {
}
