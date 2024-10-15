package ci.org.recycle.services.dtos.responses;

import ci.org.recycle.models.enumerations.ActionWaste;
import ci.org.recycle.models.enumerations.ConditionWaste;


import java.util.UUID;

public record WasteResponseForCitizenDTO(
         UUID id,
         float weight,
         ConditionWaste condition,
         String urlImage,
         String details,
         ActionWaste action,
         TypeWasteResponseDTO typeWaste,
         DepositResponseDTO deposit
) {
}
