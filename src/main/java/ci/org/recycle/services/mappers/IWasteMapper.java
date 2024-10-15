package ci.org.recycle.services.mappers;

import ci.org.recycle.models.Waste;
import ci.org.recycle.services.dtos.responses.WasteResponseForCitizenDTO;
import ci.org.recycle.services.dtos.responses.WasteResponseForPickerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface IWasteMapper {

    @Mappings({
            @Mapping(source = "entity.id", target = "id"),
            @Mapping(source = "entity.weight", target = "weight"),
            @Mapping(source = "entity.condition", target = "condition"),
            @Mapping(source = "entity.urlImage", target = "urlImage"),
            @Mapping(source = "entity.details", target = "details"),
            @Mapping(source = "entity.action", target = "action"),
            @Mapping(source = "entity.typeWaste", target = "typeWaste"),
            @Mapping(source = "entity.typeWaste.id", target = "typeWaste.id"),
            @Mapping(source = "entity.typeWaste.description", target = "typeWaste.description"),
            @Mapping(source = "entity.deposit", target = "deposit"),
            @Mapping(source = "entity.deposit.id", target = "deposit.id"),
            @Mapping(source = "entity.deposit.depositDate", target = "deposit.depositDate"),
            @Mapping(source = "entity.deposit.statusDeposit", target = "deposit.statusDeposit"),
            @Mapping(source = "entity.deposit.collectionPoint", target = "deposit.collectionPoint"),
            @Mapping(source = "entity.deposit.collectionPoint.id", target = "deposit.collectionPoint.id"),
            @Mapping(source = "entity.deposit.collectionPoint.description", target = "deposit.collectionPoint.description"),
            @Mapping(source = "entity.deposit.collectionPoint.latitude", target = "deposit.collectionPoint.latitude"),
            @Mapping(source = "entity.deposit.collectionPoint.longitude", target = "deposit.collectionPoint.longitude"),
    })
    WasteResponseForCitizenDTO toDto(Waste entity);


    @Mappings({
            @Mapping(source = "entity.id", target = "id"),
            @Mapping(source = "entity.weight", target = "weight"),
            @Mapping(source = "entity.condition", target = "condition"),
            @Mapping(source = "entity.urlImage", target = "urlImage"),
            @Mapping(source = "entity.details", target = "details"),
            @Mapping(source = "entity.action", target = "action"),
            @Mapping(source = "entity.typeWaste", target = "typeWaste"),
            @Mapping(source = "entity.typeWaste.id", target = "typeWaste.id"),
            @Mapping(source = "entity.typeWaste.description", target = "typeWaste.description"),
            @Mapping(source = "entity.deposit", target = "deposit"),
            @Mapping(source = "entity.deposit.id", target = "deposit.id"),
            @Mapping(source = "entity.deposit.depositDate", target = "deposit.depositDate"),
            @Mapping(source = "entity.deposit.statusDeposit", target = "deposit.statusDeposit"),
            @Mapping(source = "entity.deposit.citizen", target = "deposit.citizenResponseDTO"),
            @Mapping(source = "entity.deposit.citizen.id", target = "deposit.citizenResponseDTO.id"),
            @Mapping(source = "entity.deposit.citizen.loyaltyPoint", target = "deposit.citizenResponseDTO.loyaltyPoint"),
            @Mapping(source = "entity.deposit.citizen.citizenType", target = "deposit.citizenResponseDTO.citizenType"),
    })
    WasteResponseForPickerDTO toDtoPicker(Waste entity);
}
