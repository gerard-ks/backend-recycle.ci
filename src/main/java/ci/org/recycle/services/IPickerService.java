package ci.org.recycle.services;

import ci.org.recycle.services.dtos.requests.DiagnosticRequestDTO;
import ci.org.recycle.services.dtos.requests.UpdateDepositRequestDTO;
import ci.org.recycle.services.dtos.requests.UpdateRequestCollectionDTO;
import ci.org.recycle.services.dtos.responses.WasteResponsesForPickerDTO;
import ci.org.recycle.web.exceptions.*;

import java.util.List;

public interface IPickerService {
    void updateRequestCollection(UpdateRequestCollectionDTO updateRequestCollectionDTO) throws CollectionRequestNotFoundException;

    void updateDeposit(UpdateDepositRequestDTO updateDepositRequestDTO) throws DepositNotFoundException;

    void diagnosticWaste(DiagnosticRequestDTO diagnosticRequestDTO) throws RepairerNotFoundException, WasteNotFoundException;

    WasteResponsesForPickerDTO getAllWasteForPicker(int pageNo, int pageSize, String sortBy, String sortDir, String querySearch) throws MyUserNotFoundException;
}
