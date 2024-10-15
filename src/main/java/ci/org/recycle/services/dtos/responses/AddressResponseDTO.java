package ci.org.recycle.services.dtos.responses;

public record AddressResponseDTO(
        String id,
        String town,
        String district,
        String city
) {
}
