package ci.org.recycle.web.resources;

import ci.org.recycle.services.IAdminService;
import ci.org.recycle.services.dtos.requests.*;
import ci.org.recycle.services.dtos.responses.*;
import ci.org.recycle.utils.Constants;
import ci.org.recycle.web.exceptions.CollectionPointNotFoundException;
import ci.org.recycle.web.exceptions.MyRoleNotFoundException;
import ci.org.recycle.web.exceptions.PickerNotFoundException;
import ci.org.recycle.web.exceptions.RepairerNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminResource {

    private final IAdminService adminService;

    @PostMapping("/createRepairer")
    @Operation(security = { @SecurityRequirement(name = "bearerAuth") })
    public ResponseEntity<RepairerResponseDTO> createRepairer(@RequestBody RepairerRequestDTO repairerRequestDTO) throws MyRoleNotFoundException {
        return new ResponseEntity<>(adminService.createAccountRepairer(repairerRequestDTO), HttpStatus.CREATED);
    }

    @PostMapping("/createPicker")
    @Operation(security = { @SecurityRequirement(name = "bearerAuth") })
    public ResponseEntity<PickerResponseDTO> createPicker(@RequestBody PickerRequestDTO pickerRequestDTO) throws MyRoleNotFoundException, CollectionPointNotFoundException {
        return new ResponseEntity<>(adminService.createAccountPicker(pickerRequestDTO), HttpStatus.CREATED);
    }

    @PostMapping("/createCollectionPoint")
    @Operation(security = { @SecurityRequirement(name = "bearerAuth") })
    public ResponseEntity<AllCollectionPointResponseDTO> createCollectionPoint(@RequestBody CollectionPointRequestDTO collectionPointRequestDTO) {
        return new ResponseEntity<>(adminService.createCollectionPoint(collectionPointRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/citizens")
    @Operation(security = { @SecurityRequirement(name = "bearerAuth") })
    public ResponseEntity<CitizenResponsesDTO> getCitizens(@RequestParam(defaultValue = Constants.DEFAULT_PAGE_NUMBER) int pageNo,
                                                           @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize,
                                                           @RequestParam(defaultValue = Constants.DEFAULT_SORT_BY) String sortBy,
                                                           @RequestParam(defaultValue = Constants.DEFAULT_SORT_DIR) String sortDirection,
                                                           @RequestParam(required = false) String querySearch) {
       CitizenResponsesDTO citizenResponsesDTO = adminService.listAccountCitizen(pageNo, pageSize, sortBy, sortDirection, querySearch);
       log.info("CitizenResponsesDTO: {}", citizenResponsesDTO);
        return new ResponseEntity<>(citizenResponsesDTO, HttpStatus.OK);
    }

    @GetMapping("/repairers")
    @Operation(security = { @SecurityRequirement(name = "bearerAuth") })
    public ResponseEntity<RepairerResponsesDTO> getRepairers(@RequestParam(defaultValue = Constants.DEFAULT_PAGE_NUMBER) int pageNo,
                                                             @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize,
                                                             @RequestParam(defaultValue = Constants.DEFAULT_SORT_BY) String sortBy,
                                                             @RequestParam(defaultValue = Constants.DEFAULT_SORT_DIR) String sortDirection,
                                                             @RequestParam(required = false) String querySearch) {
        return new ResponseEntity<>(adminService.listAccountRepairer(pageNo, pageSize, sortBy, sortDirection, querySearch), HttpStatus.OK);
    }

    @GetMapping("/pickers")
    @Operation(security = { @SecurityRequirement(name = "bearerAuth") })
    public ResponseEntity<PickerResponsesDTO> getPickers( @RequestParam(defaultValue = Constants.DEFAULT_PAGE_NUMBER) int pageNo,
                                                          @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize,
                                                          @RequestParam(defaultValue = Constants.DEFAULT_SORT_BY) String sortBy,
                                                          @RequestParam(defaultValue = Constants.DEFAULT_SORT_DIR) String sortDirection,
                                                          @RequestParam(required = false) String querySearch) {
        return new ResponseEntity<>(adminService.listAccountPicker(pageNo, pageSize, sortBy, sortDirection, querySearch), HttpStatus.OK);
    }

    @PostMapping("/createTypeWaste")
    @Operation(security = { @SecurityRequirement(name = "bearerAuth") })
    public ResponseEntity<TypeWasteResponseDTO> createTypeWaste(@RequestBody @Valid TypeWasteRequestDTO requestDTO){
        return new ResponseEntity<>(adminService.createTypeWaste(requestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/repairer")
    @Operation(security = { @SecurityRequirement(name = "bearerAuth") })
    public ResponseEntity<RepairerResponseDTO> updateRepairer(@RequestBody RepairerRequestUpdateDTO repairerRequestUpdateDTO) throws RepairerNotFoundException {
        return new ResponseEntity<>(adminService.updateAccountRepairer(repairerRequestUpdateDTO), HttpStatus.OK);
    }

    @PutMapping("/picker")
    @Operation(security = { @SecurityRequirement(name = "bearerAuth") })
    public ResponseEntity<PickerResponseDTO> updatePicker(@RequestBody PickerRequestUpdateDTO pickerRequestUpdateDTO) throws PickerNotFoundException {
        return new ResponseEntity<>(adminService.updateAccountPicker(pickerRequestUpdateDTO), HttpStatus.OK);
    }

    @PutMapping("/collectionPoint")
    @Operation(security = { @SecurityRequirement(name = "bearerAuth") })
    public ResponseEntity<AllCollectionPointResponseDTO> updateCollectionPoint(@RequestBody CollectionPointRequestUpdateDTO collectionPointRequestUpdateDTO) throws CollectionPointNotFoundException {
        return new ResponseEntity<>(adminService.updateCollectionPoint(collectionPointRequestUpdateDTO), HttpStatus.OK);
    }


}
