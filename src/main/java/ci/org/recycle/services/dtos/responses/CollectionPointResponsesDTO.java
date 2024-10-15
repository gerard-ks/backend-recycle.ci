package ci.org.recycle.services.dtos.responses;

import java.util.List;

public record CollectionPointResponsesDTO(
        List<AllCollectionPointResponseDTO> data,
        int pageNo,
        int pageSize,
        long totalElements,
        boolean last,
        int totalPages
) {
}
