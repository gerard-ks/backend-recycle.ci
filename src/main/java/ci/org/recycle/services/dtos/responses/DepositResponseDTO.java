package ci.org.recycle.services.dtos.responses;

import ci.org.recycle.models.enumerations.StatusDeposit;

import java.time.LocalDate;
import java.util.UUID;

public record DepositResponseDTO(
        UUID id,
        LocalDate depositDate,
        StatusDeposit statusDeposit,
        CollectionPointResponseDTO collectionPoint
) {
}
