package ci.org.recycle.services.dtos.responses;

import java.util.List;

public record RepairerResponsesDTO(
        List<RepairerResponseDTO> data,
        int pageNo,
        int pageSize,
        long totalElements,
        boolean last,
        int totalPages
) {
}
