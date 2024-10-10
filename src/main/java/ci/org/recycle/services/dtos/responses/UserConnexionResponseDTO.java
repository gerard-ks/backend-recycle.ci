package ci.org.recycle.services.dtos.responses;

public record UserConnexionResponseDTO(
        String firstName,
        String lastName,
        String email,
        RoleConnexionResponseDTO role
) {
}
