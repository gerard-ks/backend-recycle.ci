package ci.org.recycle.web.resources;

import ci.org.recycle.services.IRepairerService;
import ci.org.recycle.services.dtos.requests.ReportDiagnosticRequestDTO;
import ci.org.recycle.services.dtos.requests.UpdateDiagnosisRequestDTO;
import ci.org.recycle.services.dtos.responses.DiagnosticResponsesForRepairerDTO;
import ci.org.recycle.utils.Constants;
import ci.org.recycle.web.exceptions.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/repairer")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class RepairerResource {

    private final IRepairerService repairerService;

    @PutMapping("/updateDiagnosis")
    public ResponseEntity<Void> updateDiagnosis(@RequestBody UpdateDiagnosisRequestDTO updateDiagnosisRequestDTO) throws DiagnosticNotFoundException {
        repairerService.updateDiagnosis(updateDiagnosisRequestDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/diagnosis")
    public ResponseEntity<DiagnosticResponsesForRepairerDTO> getAllDiagnosisRepairer( @RequestParam(defaultValue = Constants.DEFAULT_PAGE_NUMBER) int pageNo,
                                                                                      @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize,
                                                                                      @RequestParam(defaultValue = Constants.DEFAULT_SORT_BY) String sortBy,
                                                                                      @RequestParam(defaultValue = Constants.DEFAULT_SORT_DIR) String sortDirection) throws MyUserNotFoundException {
        return new ResponseEntity<>(repairerService.getAllDiagnosisRepairer(pageNo, pageSize, sortBy, sortDirection), HttpStatus.OK);
    }

    @PostMapping("/sendReportDiagnostic")
    public ResponseEntity<Void> sendReportDiagnostic(@RequestPart("file") MultipartFile file, @RequestPart("report") ReportDiagnosticRequestDTO diagnosticRequestDTO) throws FileUploadFailedException, IOException, NoFileProvidedException, RepairerNotFoundException {
        repairerService.sendReportDiagnostic(file, diagnosticRequestDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
