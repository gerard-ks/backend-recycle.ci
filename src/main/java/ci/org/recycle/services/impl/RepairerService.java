package ci.org.recycle.services.impl;


import ci.org.recycle.models.Diagnostic;
import ci.org.recycle.models.Repairer;
import ci.org.recycle.models.ReportDiagnostic;
import ci.org.recycle.repositories.IDiagnosticRepository;
import ci.org.recycle.repositories.IRepairerRepository;
import ci.org.recycle.repositories.IReportDiagnosticRepository;
import ci.org.recycle.services.IRepairerService;
import ci.org.recycle.services.IUserService;
import ci.org.recycle.services.dtos.requests.ReportDiagnosticRequestDTO;
import ci.org.recycle.services.dtos.requests.UpdateDiagnosisRequestDTO;
import ci.org.recycle.services.dtos.responses.CurrentUserResponseDTO;
import ci.org.recycle.services.dtos.responses.DiagnosticResponseForRepairerDTO;
import ci.org.recycle.services.dtos.responses.DiagnosticResponsesForRepairerDTO;
import ci.org.recycle.services.mappers.IDiagnosticMapper;
import ci.org.recycle.web.exceptions.*;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class RepairerService implements IRepairerService {

    private final IDiagnosticRepository diagnosticRepository;
    private final IReportDiagnosticRepository reportDiagnosticRepository;
    private final IRepairerRepository repairerRepository;
    private final IUserService userService;
    private final IDiagnosticMapper diagnosticMapper;
    private final Cloudinary cloudinary;


    @Override
    public void updateDiagnosis(UpdateDiagnosisRequestDTO updateDiagnosisRequestDTO) throws DiagnosticNotFoundException {
        Diagnostic diagnostic = diagnosticRepository.findById(updateDiagnosisRequestDTO.diagnosisId()).orElseThrow(() -> new DiagnosticNotFoundException("diagnostic not found"));
        diagnostic.setStatusDiagnostic(updateDiagnosisRequestDTO.statusDiagnostic());
        diagnosticRepository.save(diagnostic);
    }

    @Override
    public DiagnosticResponsesForRepairerDTO getAllDiagnosisRepairer(int pageNo, int pageSize, String sortBy, String sortDir) throws MyUserNotFoundException {

        CurrentUserResponseDTO currentUserResponseDTO = userService.getCurrentUser();
        Repairer repairer = repairerRepository.findByUserId(currentUserResponseDTO.id()).orElseThrow(()-> new MyUserNotFoundException("user not found"));

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Diagnostic> diagnostics = diagnosticRepository.findWastesByRepairerId(repairer.getId(), pageable);

        List<Diagnostic> allDiagnostics = diagnostics.getContent();
        List<DiagnosticResponseForRepairerDTO> dtos = allDiagnostics.stream().map(diagnosticMapper::toDto).toList();


        return new DiagnosticResponsesForRepairerDTO(
                 dtos,
                diagnostics.getNumber() + 1,
                diagnostics.getSize(),
                diagnostics.getTotalElements(),
                diagnostics.isLast(),
                diagnostics.getTotalPages()
        );
    }

    @Override
    public void sendReportDiagnostic(MultipartFile file, ReportDiagnosticRequestDTO diagnosticRequestDTO) throws RepairerNotFoundException, IOException, FileUploadFailedException, NoFileProvidedException {
        Repairer repairer = repairerRepository.findById(diagnosticRequestDTO.repairerId()).orElseThrow(() -> new RepairerNotFoundException("repairer not found"));
        ReportDiagnostic reportDiagnostic = new ReportDiagnostic();
        reportDiagnostic.setServicePrice(reportDiagnostic.getServicePrice());
        reportDiagnostic.setRepairer(repairer);

        if(file != null && !file.isEmpty()) {
            Map params1 = ObjectUtils.asMap(
                    "use_filename", true,
                    "unique_filename", true, // S'assurez que le nom de fichier est unique
                    "overwrite", false, // Ne pas écraser les fichiers existants
                    "resource_type",
                    "raw"
            );

            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), params1);
            String urlFile = uploadResult.get("url").toString();

            if (urlFile != null && !urlFile.isEmpty()) {
                reportDiagnostic.setUrlDocument(urlFile);
                reportDiagnosticRepository.save(reportDiagnostic);
            }else {
                throw new FileUploadFailedException("File upload failed, URL not available.");
            }
        }else {
            throw new NoFileProvidedException("No file provided for upload.");
        }

    }
}
