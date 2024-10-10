package ci.org.recycle.services.dtos.responses;

import java.time.Instant;

public record JWTTokenResponseDTO(
        String accessToken,
        String refreshToken,
        Instant tokenExpiryDate,
        UserConnexionResponseDTO userConnexionResponseDTO
) {
}
