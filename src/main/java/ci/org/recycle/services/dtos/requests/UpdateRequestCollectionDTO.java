package ci.org.recycle.services.dtos.requests;

import ci.org.recycle.models.enumerations.StatusRequest;

import java.util.UUID;

public record UpdateRequestCollectionDTO(
        UUID collectionRequestId,
        StatusRequest statusRequest
) {
}
