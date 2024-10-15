package ci.org.recycle.services.impl;

import ci.org.recycle.models.*;
import ci.org.recycle.models.enumerations.StatusDeposit;
import ci.org.recycle.models.enumerations.StatusRequest;
import ci.org.recycle.repositories.*;
import ci.org.recycle.services.ICitizenService;
import ci.org.recycle.services.IUserService;
import ci.org.recycle.services.dtos.requests.CollectionRequestHomeDTO;
import ci.org.recycle.services.dtos.requests.DepositRequestDTO;
import ci.org.recycle.services.dtos.requests.WasteRequestDTO;
import ci.org.recycle.services.dtos.responses.CurrentUserResponseDTO;
import ci.org.recycle.services.dtos.responses.RepairerResponsesDTO;
import ci.org.recycle.services.dtos.responses.WasteResponseForCitizenDTO;
import ci.org.recycle.services.dtos.responses.WasteResponsesForCitizenDTO;
import ci.org.recycle.services.mappers.IWasteMapper;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CitizenService implements ICitizenService {

    private final ICitizenRepository citizenRepository;
    private final IUserService userService;
    private final ICollectionPointRepository collectionPointRepository;
    private final ITypeWasteRepository typeWasteRepository;
    private final IDepositRepository depositRepository;
    private final IWasteRepository wasteRepository;
    private final ICollectionRequestRepository collectionRequestRepository;
    private final Cloudinary cloudinary;
    private final IWasteMapper wasteMapper;

    @Override
    public void deposit(MultipartFile file, DepositRequestDTO depositRequestDTO) throws CitizenNotFoundException, CollectionPointNotFoundException, TypeWasteNotFoundException, IOException {
        Citizen citizen = citizenRepository.findById(depositRequestDTO.citizenId()).orElseThrow(() -> new CitizenNotFoundException("Citizen not found"));
        CollectionPoint collectionPoint = collectionPointRepository.findById(depositRequestDTO.collectionPointId()).orElseThrow(() -> new CollectionPointNotFoundException("Collection point not found"));

        Deposit deposit = new Deposit();
        deposit.setCitizen(citizen);
        deposit.setStatusDeposit(StatusDeposit.PENDING);
        deposit.setCollectionPoint(collectionPoint);
        deposit.setDepositDate(LocalDate.now());
        Deposit saveDeposit = depositRepository.save(deposit);


        for (WasteRequestDTO wasteRequestDTO : depositRequestDTO.wasteRequestDTOS()) {
            TypeWaste typeWaste = typeWasteRepository.findById(wasteRequestDTO.typeWasteId()).orElseThrow(()-> new TypeWasteNotFoundException("Type waste not found"));
            Waste waste = new Waste();
            waste.setTypeWaste(typeWaste);
            waste.setAction(wasteRequestDTO.action());
            waste.setCondition(wasteRequestDTO.conditionWaste());
            waste.setDetails(wasteRequestDTO.details());
            waste.setWeight(wasteRequestDTO.weight());
            waste.setDeposit(saveDeposit);

            if(file != null && !file.isEmpty()) {
                Map params1 = ObjectUtils.asMap(
                        "use_filename", true,
                        "unique_filename", true, // S'assurez que le nom de fichier est unique
                        "overwrite", false // Ne pas écraser les fichiers existants
                );

                Map uploadResult = cloudinary.uploader().upload(file.getBytes(), params1);
                String urlFile = uploadResult.get("url").toString();

                waste.setUrlImage(urlFile);
            }


            wasteRepository.save(waste);
        }

    }

    @Override
    public void requestCollection(CollectionRequestHomeDTO collectionRequestHomeDTO) throws DepositNotFoundException {
        Deposit deposit = depositRepository.findById(collectionRequestHomeDTO.depositId()).orElseThrow(() -> new DepositNotFoundException("deposit not found"));

        CollectionRequest collectionRequest = new CollectionRequest();
        collectionRequest.setDeposit(deposit);
        collectionRequest.setStatusRequest(StatusRequest.PENDING);
        collectionRequest.setLongitude(collectionRequestHomeDTO.longitude());
        collectionRequest.setLatitude(collectionRequestHomeDTO.latitude());
        collectionRequestRepository.save(collectionRequest);

    }

    @Override
    public WasteResponsesForCitizenDTO getAllWasteForCitizen(int pageNo, int pageSize, String sortBy, String sortDir) throws MyUserNotFoundException {

        CurrentUserResponseDTO currentUserResponseDTO = userService.getCurrentUser();
        Citizen citizen = citizenRepository.findByUserId(currentUserResponseDTO.id()).orElseThrow(()-> new MyUserNotFoundException("user not found"));

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Waste> wastes = wasteRepository.findWastesByCitizenId(citizen.getId(), pageable);


        List<Waste> allWastes = wastes.getContent();
        List<WasteResponseForCitizenDTO> dtos = allWastes.stream().map(wasteMapper::toDto).toList();

        return new WasteResponsesForCitizenDTO(
                dtos,
                wastes.getNumber() + 1,
                wastes.getSize(),
                wastes.getTotalElements(),
                wastes.isLast(),
                wastes.getTotalPages()
        );
    }

}
