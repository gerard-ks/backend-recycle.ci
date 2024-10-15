package ci.org.recycle.services.impl;

import ci.org.recycle.models.Picker;
import ci.org.recycle.models.User;
import ci.org.recycle.repositories.IPickerRepository;
import ci.org.recycle.repositories.IUserRepository;
import ci.org.recycle.services.IAuthentificationService;
import ci.org.recycle.services.IJwtService;
import ci.org.recycle.services.dtos.requests.RefreshTokenRequestDTO;
import ci.org.recycle.services.dtos.requests.LoginRequestDTO;
import ci.org.recycle.services.dtos.responses.JWTTokenResponseDTO;
import ci.org.recycle.services.dtos.responses.RoleConnexionResponseDTO;
import ci.org.recycle.services.dtos.responses.UserConnexionResponseDTO;
import ci.org.recycle.utils.SecurityUtils;
import ci.org.recycle.web.exceptions.MyUserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthentificationService implements IAuthentificationService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final IJwtService jwtService;
    private final IUserRepository userRepository;


    @Override
    public JWTTokenResponseDTO autorize(LoginRequestDTO login) throws MyUserNotFoundException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                login.email(), login.password()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        log.debug("authenticated user: {}", authentication.getPrincipal());

        if(authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String email = authentication.getName();
            User user = getByEmail(email);


                String accessToken = jwtService.generateToken(authentication.getAuthorities(), user, login.rememberMe());
                String refreshToken = jwtService.createRefreshToken(user.getEmail());
                Instant expiresToken = jwtService.extractExpiresAt(accessToken);

                return new JWTTokenResponseDTO(
                        accessToken,
                        refreshToken,
                        expiresToken,
                        new UserConnexionResponseDTO(
                                user.getFirstName(),
                                user.getLastName(),
                                user.getEmail(),
                                new RoleConnexionResponseDTO(
                                        user.getRole().getRoleName()
                                )
                        )
                );
        }

        return new JWTTokenResponseDTO(
                null,
                null,
                null,
                null
        );
    }


    @Override
    public JWTTokenResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO) throws MyUserNotFoundException {
        if(!jwtService.isTokenExpired(refreshTokenRequestDTO.refreshToken())){

            String email = jwtService.extractUsername(refreshTokenRequestDTO.refreshToken());

            User user = getByEmail(email);
            Collection<GrantedAuthority> authorities =  Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+ user.getRole().getRoleName()));

            String accessToken = jwtService.generateToken(authorities, user, false);
            String refreshToken = jwtService.createRefreshToken(user.getEmail());
            Instant expiresToken = jwtService.extractExpiresAt(accessToken);

            return new JWTTokenResponseDTO(
                    accessToken,
                    refreshToken,
                    expiresToken,
                    new UserConnexionResponseDTO(
                            user.getFirstName(),
                            user.getLastName(),
                            user.getEmail(),
                            new RoleConnexionResponseDTO(
                                    user.getRole().getRoleName()
                            )
                    )
            );
        }
        return new JWTTokenResponseDTO(
                null,
                null,
                null,
                null
        );
    }

    private User getByEmail(String email) throws MyUserNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(()-> new MyUserNotFoundException("user not found " + email));
    }

}
