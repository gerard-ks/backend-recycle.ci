package ci.org.recycle.services.impl;


import ci.org.recycle.models.*;
import ci.org.recycle.models.enumerations.StatusDiagnostic;
import ci.org.recycle.repositories.*;
import ci.org.recycle.services.IPickerService;
import ci.org.recycle.services.IUserService;
import ci.org.recycle.services.dtos.requests.DiagnosticRequestDTO;
import ci.org.recycle.services.dtos.requests.UpdateDepositRequestDTO;
import ci.org.recycle.services.dtos.requests.UpdateRequestCollectionDTO;
import ci.org.recycle.services.dtos.responses.CurrentUserResponseDTO;
import ci.org.recycle.services.dtos.responses.WasteResponseForPickerDTO;
import ci.org.recycle.services.dtos.responses.WasteResponsesForPickerDTO;
import ci.org.recycle.services.mappers.IWasteMapper;
import ci.org.recycle.web.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PickerService implements IPickerService {

    private final IUserService userService;
    private final IPickerRepository pickerRepository;
    private final IWasteRepository wasteRepository;
    private final IWasteMapper wasteMapper;
    private final IDepositRepository depositRepository;
    private final ICollectionRequestRepository collectionRequestRepository;
    private final IRepairerRepository repairerRepository;
    private final IDiagnosticRepository diagnosticRepository;

    @Override
    public void updateRequestCollection(UpdateRequestCollectionDTO updateRequestCollectionDTO) throws CollectionRequestNotFoundException {
        CollectionRequest collectionRequest = collectionRequestRepository.findById(updateRequestCollectionDTO.collectionRequestId()).orElseThrow(() -> new CollectionRequestNotFoundException("collection request not found"));

        if(updateRequestCollectionDTO.statusRequest() != null) {
            collectionRequest.setStatusRequest(updateRequestCollectionDTO.statusRequest());
        }

        collectionRequestRepository.save(collectionRequest);
    }

    @Override
    public void updateDeposit(UpdateDepositRequestDTO updateDepositRequestDTO) throws DepositNotFoundException {
        Deposit deposit = depositRepository.findById(updateDepositRequestDTO.depositId()).orElseThrow(()-> new DepositNotFoundException("deposit not found"));

        if(updateDepositRequestDTO.statusDeposit() != null) {
            deposit.setStatusDeposit(updateDepositRequestDTO.statusDeposit());
        }

        depositRepository.save(deposit);
    }

    @Override
    public void diagnosticWaste(DiagnosticRequestDTO diagnosticRequestDTO) throws RepairerNotFoundException, WasteNotFoundException {

        Repairer repairer = repairerRepository.findById(diagnosticRequestDTO.repairerId()).orElseThrow(() -> new RepairerNotFoundException("repairer not found") );
        Waste waste = wasteRepository.findById(diagnosticRequestDTO.dechetId()).orElseThrow(() -> new WasteNotFoundException("waste not found exception"));

        Diagnostic diagnostic = new Diagnostic();
        diagnostic.setRepairer(repairer);
        diagnostic.setWaste(waste);
        diagnostic.setStatusDiagnostic(StatusDiagnostic.DIAGNOSIS_IN);

        diagnosticRepository.save(diagnostic);
    }

    @Override
    public WasteResponsesForPickerDTO getAllWasteForPicker(int pageNo, int pageSize, String sortBy, String sortDir, String querySearch) throws MyUserNotFoundException {

        CurrentUserResponseDTO currentUserResponseDTO = userService.getCurrentUser();
        Picker picker = pickerRepository.findByUserId(currentUserResponseDTO.id()).orElseThrow(()-> new MyUserNotFoundException("user not found"));

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Waste> wastes = wasteRepository.findWastesByCollectionPointId(picker.getCollectionPoint().getId(), pageable);


        List<Waste> allWastes = wastes.getContent();
        List<WasteResponseForPickerDTO> dtos = allWastes.stream().map(wasteMapper::toDtoPicker).toList();


       return new WasteResponsesForPickerDTO(
               dtos,
               wastes.getNumber() + 1,
               wastes.getSize(),
               wastes.getTotalElements(),
               wastes.isLast(),
               wastes.getTotalPages()
       );
    }
}
