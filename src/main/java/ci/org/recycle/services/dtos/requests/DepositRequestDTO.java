package ci.org.recycle.services.dtos.requests;

import java.util.List;
import java.util.UUID;

public record DepositRequestDTO(
        UUID citizenId,
        UUID collectionPointId,
        List<WasteRequestDTO> wasteRequestDTOS
) {
}
