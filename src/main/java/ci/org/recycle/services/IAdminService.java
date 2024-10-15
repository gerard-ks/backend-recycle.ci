package ci.org.recycle.services;

import ci.org.recycle.services.dtos.requests.*;
import ci.org.recycle.services.dtos.responses.*;
import ci.org.recycle.web.exceptions.*;

import java.util.List;
import java.util.UUID;

public interface IAdminService {
    TypeWasteResponseDTO createTypeWaste(TypeWasteRequestDTO typeWasteRequestDTO);
    List<TypeWasteResponseDTO> getAllTypeWaste();
    void deleteTypeWaste(UUID id) throws TypeWasteNotFoundException;

    RepairerResponseDTO createAccountRepairer(RepairerRequestDTO repairerRequestDTO) throws MyRoleNotFoundException;
    RepairerResponsesDTO listAccountRepairer(int pageNo, int pageSize, String sortBy, String sortDir, String querySearch);
    RepairerResponseDTO updateAccountRepairer(RepairerRequestUpdateDTO repairerRequestUpdateDTO) throws RepairerNotFoundException;

    PickerResponseDTO createAccountPicker(PickerRequestDTO pickerRequestDTO) throws MyRoleNotFoundException, CollectionPointNotFoundException;
    PickerResponsesDTO listAccountPicker(int pageNo, int pageSize, String sortBy, String sortDir, String querySearch);
    PickerResponseDTO updateAccountPicker(PickerRequestUpdateDTO pickerRequestUpdateDTO) throws PickerNotFoundException;


    CitizenResponsesDTO listAccountCitizen(int pageNo, int pageSize, String sortBy, String sortDir, String querySearch);

    AllCollectionPointResponseDTO createCollectionPoint(CollectionPointRequestDTO collectionPointRequestDTO);
    AllCollectionPointResponseDTO updateCollectionPoint(CollectionPointRequestUpdateDTO collectionPointRequestUpdateDTO) throws CollectionPointNotFoundException;
}
