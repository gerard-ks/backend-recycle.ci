package ci.org.recycle.services.dtos.responses;


import java.time.LocalDate;
import java.util.UUID;

public record UserResponseDTO(
    UUID id,
    String firstName,
    String lastName,
    LocalDate birthDate,
    String telephone,
    String email,
    String password,
    boolean accountNonLocked,
    boolean enabled,
    RoleResponseDTO roleResponseDTO,
    AddressResponseDTO addressResponseDTO
) {
}
