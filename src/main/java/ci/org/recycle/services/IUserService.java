package ci.org.recycle.services;


import ci.org.recycle.services.dtos.requests.*;
import ci.org.recycle.services.dtos.responses.*;
import ci.org.recycle.web.exceptions.*;


public interface IUserService {

   CitizenResponseDTO createAccountCitizen(CitizenRequestDTO citizenRequestDTO) throws MyRoleNotFoundException;
   CitizenResponseDTO updateAccountCitizen(CitizenRequestUpdateDTO citizenRequestUpdateDTO) throws CitizenNotFoundException;
   CollectionPointResponsesDTO listCollectionPoint(int pageNo, int pageSize, String sortBy, String sortDir, Double latitude, Double longitude);
   CurrentUserResponseDTO getCurrentUser() throws MyUserNotFoundException;
}
