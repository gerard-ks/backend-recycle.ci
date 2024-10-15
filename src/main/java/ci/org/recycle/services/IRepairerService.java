package ci.org.recycle.services;


import ci.org.recycle.services.dtos.requests.ReportDiagnosticRequestDTO;
import ci.org.recycle.services.dtos.requests.UpdateDiagnosisRequestDTO;
import ci.org.recycle.services.dtos.responses.DiagnosticResponseForRepairerDTO;
import ci.org.recycle.services.dtos.responses.DiagnosticResponsesForRepairerDTO;
import ci.org.recycle.web.exceptions.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface IRepairerService {
    void updateDiagnosis(UpdateDiagnosisRequestDTO updateDiagnosisRequestDTO) throws DiagnosticNotFoundException;
    DiagnosticResponsesForRepairerDTO getAllDiagnosisRepairer(int pageNo, int pageSize, String sortBy, String sortDir) throws MyUserNotFoundException;
    void sendReportDiagnostic(MultipartFile file, ReportDiagnosticRequestDTO diagnosticRequestDTO) throws RepairerNotFoundException, IOException, FileUploadFailedException, NoFileProvidedException;
}
