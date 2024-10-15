package ci.org.recycle.web.resources;

import ci.org.recycle.services.IUserService;
import ci.org.recycle.services.dtos.requests.*;
import ci.org.recycle.services.dtos.responses.*;
import ci.org.recycle.utils.Constants;
import ci.org.recycle.web.exceptions.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserResource {

    private final IUserService userService;

    @PostMapping("/createCitizen")
    public ResponseEntity<CitizenResponseDTO> createCitizen(@RequestBody CitizenRequestDTO citizenRequestDTO) throws MyRoleNotFoundException {
        return new ResponseEntity<>(userService.createAccountCitizen(citizenRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/collectionPoints")
    public ResponseEntity<CollectionPointResponsesDTO> getCollectionPoints( @RequestParam(defaultValue = Constants.DEFAULT_PAGE_NUMBER) int pageNo,
                                                                            @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize,
                                                                            @RequestParam(defaultValue = Constants.DEFAULT_SORT_BY) String sortBy,
                                                                            @RequestParam(defaultValue = Constants.DEFAULT_SORT_DIR) String sortDirection,
                                                                            @RequestParam(required = false) Double latitude,
                                                                            @RequestParam(required = false) Double longitude) {
        return new ResponseEntity<>(userService.listCollectionPoint(pageNo, pageSize, sortBy, sortDirection, latitude, longitude), HttpStatus.OK);
    }

    @PutMapping("/citizen")
    @Operation(security = { @SecurityRequirement(name = "bearerAuth") })
    public ResponseEntity<CitizenResponseDTO> updateCitizen(@RequestBody CitizenRequestUpdateDTO citizenRequestDTO) throws CitizenNotFoundException {
        return new ResponseEntity<>(userService.updateAccountCitizen(citizenRequestDTO), HttpStatus.OK);
    }


}
