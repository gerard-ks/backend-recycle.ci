package ci.org.recycle.services.dtos.responses;

import java.util.UUID;

public record RepairerResponseDTO(
        UUID id,
        String speciality,
        Integer yearOfExperience,
        String certificate,
        UserResponseDTO userResponseDTO
) {
}
