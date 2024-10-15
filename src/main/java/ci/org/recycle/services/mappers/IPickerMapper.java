package ci.org.recycle.services.mappers;

import ci.org.recycle.models.Picker;
import ci.org.recycle.services.dtos.responses.PickerResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface IPickerMapper {

    @Mappings({
            @Mapping(source = "entity.id", target = "id"),
            @Mapping(source = "entity.serialNumber", target = "serialNumber"),
            @Mapping(source = "entity.user", target = "userResponseDTO"),
            @Mapping(source = "entity.user.id", target = "userResponseDTO.id"),
            @Mapping(source = "entity.user.firstName", target = "userResponseDTO.firstName"),
            @Mapping(source = "entity.user.lastName", target = "userResponseDTO.lastName"),
            @Mapping(source = "entity.user.birthDate", target = "userResponseDTO.birthDate"),
            @Mapping(source = "entity.user.telephone", target = "userResponseDTO.telephone"),
            @Mapping(source = "entity.user.email", target = "userResponseDTO.email"),
            @Mapping(source = "entity.user.password", target = "userResponseDTO.password", ignore = true),
            @Mapping(source = "entity.user.accountNonLocked", target = "userResponseDTO.accountNonLocked"),
            @Mapping(source = "entity.user.enabled", target = "userResponseDTO.enabled"),
            @Mapping(source = "entity.user.role", target = "userResponseDTO.roleResponseDTO"),
            @Mapping(source = "entity.user.role.id", target = "userResponseDTO.roleResponseDTO.id"),
            @Mapping(source = "entity.user.role.roleName", target = "userResponseDTO.roleResponseDTO.description"),
            @Mapping(source = "entity.user.address", target = "userResponseDTO.addressResponseDTO"),
            @Mapping(source = "entity.user.address.id", target = "userResponseDTO.addressResponseDTO.id"),
            @Mapping(source = "entity.user.address.town", target = "userResponseDTO.addressResponseDTO.town"),
            @Mapping(source = "entity.user.address.district", target = "userResponseDTO.addressResponseDTO.district"),
            @Mapping(source = "entity.user.address.city", target = "userResponseDTO.addressResponseDTO.city"),
            @Mapping(source = "entity.statut", target = "statut"),
            @Mapping(source = "entity.collectionPoint", target = "collectionPointResponseDTO"),
            @Mapping(source = "entity.collectionPoint.id", target = "collectionPointResponseDTO.id"),
            @Mapping(source = "entity.collectionPoint.description", target = "collectionPointResponseDTO.description"),
            @Mapping(source = "entity.collectionPoint.latitude", target = "collectionPointResponseDTO.latitude"),
            @Mapping(source = "entity.collectionPoint.longitude", target = "collectionPointResponseDTO.longitude"),
    })
    PickerResponseDTO toDto(Picker entity);
}
