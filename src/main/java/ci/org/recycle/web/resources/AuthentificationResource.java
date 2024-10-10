package ci.org.recycle.web.resources;

import ci.org.recycle.services.IAuthentificationService;
import ci.org.recycle.services.dtos.requests.LoginRequestDTO;
import ci.org.recycle.services.dtos.requests.RefreshTokenRequestDTO;
import ci.org.recycle.services.dtos.responses.JWTTokenResponseDTO;
import ci.org.recycle.web.exceptions.MyUserNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthentificationResource {
  private final IAuthentificationService authentificationService;

    public AuthentificationResource(IAuthentificationService authentificationService) {
        this.authentificationService = authentificationService;
    }

    @PostMapping("/login")
    public ResponseEntity<JWTTokenResponseDTO> authenticate(@Valid @RequestBody LoginRequestDTO loginRequestDTO) throws MyUserNotFoundException {
        log.debug("Authenticating {}", loginRequestDTO);
        return new ResponseEntity<>(authentificationService.autorize(loginRequestDTO), HttpStatus.OK);
    }


    @PostMapping("/refreshToken")
    public ResponseEntity<JWTTokenResponseDTO> refreshToken(@Valid @RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) throws MyUserNotFoundException {
        return new ResponseEntity<>(authentificationService.refreshToken(refreshTokenRequestDTO), HttpStatus.OK);
    }
}
