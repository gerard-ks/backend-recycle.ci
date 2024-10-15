package ci.org.recycle.services.dtos.requests;

import ci.org.recycle.models.enumerations.StatusDeposit;

import java.util.UUID;

public record UpdateDepositRequestDTO(
        UUID depositId,
        StatusDeposit statusDeposit
) {
}
