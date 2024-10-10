package ci.org.recycle.services;

import ci.org.recycle.services.dtos.requests.RefreshTokenRequestDTO;
import ci.org.recycle.services.dtos.responses.JWTTokenResponseDTO;
import ci.org.recycle.services.dtos.requests.LoginRequestDTO;
import ci.org.recycle.web.exceptions.MyUserNotFoundException;


public interface IAuthentificationService {
    JWTTokenResponseDTO autorize(LoginRequestDTO login) throws MyUserNotFoundException;
    JWTTokenResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO) throws MyUserNotFoundException;
}
