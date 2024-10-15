package ci.org.recycle.services.dtos.requests;

import ci.org.recycle.models.enumerations.ActionWaste;
import ci.org.recycle.models.enumerations.ConditionWaste;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


public record WasteRequestDTO(
        float weight,
        ConditionWaste conditionWaste,
        String details,
        ActionWaste action,
        UUID typeWasteId
) {
}
