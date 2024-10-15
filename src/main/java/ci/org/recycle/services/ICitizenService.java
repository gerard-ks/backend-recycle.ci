package ci.org.recycle.services;

import ci.org.recycle.services.dtos.requests.CollectionRequestHomeDTO;
import ci.org.recycle.services.dtos.requests.DepositRequestDTO;
import ci.org.recycle.services.dtos.responses.WasteResponseForCitizenDTO;
import ci.org.recycle.services.dtos.responses.WasteResponsesForCitizenDTO;
import ci.org.recycle.web.exceptions.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ICitizenService {
    void deposit(MultipartFile file, DepositRequestDTO depositRequestDTO) throws CitizenNotFoundException, CollectionPointNotFoundException, TypeWasteNotFoundException, IOException;
    void requestCollection(CollectionRequestHomeDTO collectionRequestHomeDTO) throws DepositNotFoundException;
    WasteResponsesForCitizenDTO getAllWasteForCitizen(int pageNo, int pageSize, String sortBy, String sortDir) throws MyUserNotFoundException;
}
