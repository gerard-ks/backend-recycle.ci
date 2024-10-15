package ci.org.recycle.services.dtos.responses;

import ci.org.recycle.models.enumerations.StatutAgent;

import java.util.UUID;

public record PickerResponseDTO(
         UUID id,
         String serialNumber,
         StatutAgent statut,
         UserResponseDTO userResponseDTO,
         CollectionPointResponseDTO collectionPointResponseDTO
) {
}
