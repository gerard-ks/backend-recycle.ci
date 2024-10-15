package ci.org.recycle.web.resources;

import ci.org.recycle.services.ICitizenService;
import ci.org.recycle.services.dtos.requests.CollectionRequestHomeDTO;
import ci.org.recycle.services.dtos.requests.DepositRequestDTO;
import ci.org.recycle.services.dtos.responses.WasteResponsesForCitizenDTO;
import ci.org.recycle.utils.Constants;
import ci.org.recycle.web.exceptions.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/citizen")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class CitizenResource {

    private final ICitizenService citizenService;

    @PostMapping(value = "/deposit")
    public ResponseEntity<Void> deposit(@RequestPart(value = "file",required = false) MultipartFile file, @RequestPart("deposit") DepositRequestDTO depositRequestDTO) throws CollectionPointNotFoundException, TypeWasteNotFoundException, IOException, CitizenNotFoundException {
        citizenService.deposit(file, depositRequestDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/requestCollection")
    public ResponseEntity<Void> requestCollection(CollectionRequestHomeDTO collectionRequestHomeDTO) throws DepositNotFoundException {
        citizenService.requestCollection(collectionRequestHomeDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/waste")
    public ResponseEntity<WasteResponsesForCitizenDTO> getAllWasteForCitizen(
            @RequestParam(defaultValue = Constants.DEFAULT_PAGE_NUMBER) int pageNo,
            @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(defaultValue = Constants.DEFAULT_SORT_BY) String sortBy,
            @RequestParam(defaultValue = Constants.DEFAULT_SORT_DIR) String sortDirection
    ) throws MyUserNotFoundException {
        return new ResponseEntity<>(citizenService.getAllWasteForCitizen(pageNo, pageSize, sortBy, sortDirection), HttpStatus.OK);
    }
}
