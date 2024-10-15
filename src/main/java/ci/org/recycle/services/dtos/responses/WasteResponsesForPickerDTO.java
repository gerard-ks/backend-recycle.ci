package ci.org.recycle.services.dtos.responses;

import java.util.List;

public record WasteResponsesForPickerDTO(
        List<WasteResponseForPickerDTO> data,
        int pageNo,
        int pageSize,
        long totalElements,
        boolean last,
        int totalPages
) {
}
