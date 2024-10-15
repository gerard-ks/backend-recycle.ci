package ci.org.recycle.services.dtos.responses;

import ci.org.recycle.models.enumerations.CitizenType;

import java.util.UUID;

public record CitizenResponseForPickerDTO(
        UUID id,
        Long loyaltyPoint,
        CitizenType citizenType
) {
}
