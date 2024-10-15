package ci.org.recycle.services.dtos.responses;

import ci.org.recycle.models.enumerations.StatusDeposit;

import java.time.LocalDate;
import java.util.UUID;

public record DepositResponseForPickerDTO(
        UUID id,
        LocalDate depositDate,
        StatusDeposit statusDeposit,
        CitizenResponseForPickerDTO citizenResponseDTO
) {
}
