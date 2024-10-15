package ci.org.recycle.services.dtos.responses;

import ci.org.recycle.models.TypeWaste;
import ci.org.recycle.models.enumerations.ConditionWaste;

import java.util.UUID;

public record WasteResponseForRepairerDTO(
        UUID id,
        float weight,
        ConditionWaste conditionWaste,
        String urlPhoto,
        String details,
        TypeWasteResponseDTO typeWaste
) {
}
