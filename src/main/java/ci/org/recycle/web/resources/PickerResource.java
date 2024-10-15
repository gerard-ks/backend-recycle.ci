package ci.org.recycle.web.resources;

import ci.org.recycle.services.IPickerService;
import ci.org.recycle.services.dtos.requests.DiagnosticRequestDTO;
import ci.org.recycle.services.dtos.requests.UpdateDepositRequestDTO;
import ci.org.recycle.services.dtos.requests.UpdateRequestCollectionDTO;
import ci.org.recycle.services.dtos.responses.CollectionPointResponsesDTO;
import ci.org.recycle.services.dtos.responses.WasteResponsesForPickerDTO;
import ci.org.recycle.services.impl.PickerService;
import ci.org.recycle.utils.Constants;
import ci.org.recycle.web.exceptions.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/picker")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class PickerResource {
    private final IPickerService pickerService;

    @PostMapping("/diagnostic")
    public ResponseEntity<Void> diagnosticWaste(@RequestBody DiagnosticRequestDTO diagnosticRequestDTO) throws WasteNotFoundException, RepairerNotFoundException {
        pickerService.diagnosticWaste(diagnosticRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/waste")
    public ResponseEntity<WasteResponsesForPickerDTO> getAllWasteForPicker(@RequestParam(defaultValue = Constants.DEFAULT_PAGE_NUMBER) int pageNo,
                                                                          @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize,
                                                                          @RequestParam(defaultValue = Constants.DEFAULT_SORT_BY) String sortBy,
                                                                          @RequestParam(defaultValue = Constants.DEFAULT_SORT_DIR) String sortDirection, @RequestParam(required = false) String querySearch) throws MyUserNotFoundException {
        return new ResponseEntity<>(pickerService.getAllWasteForPicker(pageNo, pageSize, sortBy, sortDirection, querySearch), HttpStatus.OK);
    }


    @PutMapping("/updateRequestCollection")
    public ResponseEntity<Void> updateRequestCollection(@RequestBody UpdateRequestCollectionDTO updateRequestCollectionDTO) throws CollectionRequestNotFoundException {
        pickerService.updateRequestCollection(updateRequestCollectionDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/updateDeposit")
    public ResponseEntity<Void> updateDeposit(@RequestBody UpdateDepositRequestDTO updateDepositRequestDTO) throws DepositNotFoundException {
        pickerService.updateDeposit(updateDepositRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
