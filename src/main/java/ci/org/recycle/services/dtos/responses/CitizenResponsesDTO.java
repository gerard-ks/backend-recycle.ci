package ci.org.recycle.services.dtos.responses;

import java.util.List;

public record CitizenResponsesDTO(
        List<CitizenResponseDTO> data,
        int pageNo,
        int pageSize,
        long totalElements,
        boolean last,
        int totalPages
) {
}
