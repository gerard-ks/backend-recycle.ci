package ci.org.recycle.services.impl;

import ci.org.recycle.models.Picker;
import ci.org.recycle.models.User;
import ci.org.recycle.repositories.IPickerRepository;
import ci.org.recycle.repositories.IUserRepository;
import ci.org.recycle.services.IAuthentificationService;
import ci.org.recycle.services.dtos.requests.RefreshTokenRequestDTO;
import ci.org.recycle.services.dtos.requests.LoginRequestDTO;
import ci.org.recycle.services.dtos.responses.JWTTokenResponseDTO;
import ci.org.recycle.services.dtos.responses.RoleConnexionResponseDTO;
import ci.org.recycle.services.dtos.responses.UserConnexionResponseDTO;
import ci.org.recycle.utils.SecurityUtils;
import ci.org.recycle.web.exceptions.MyUserNotFoundException;
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
@Slf4j
public class AuthentificationService implements IAuthentificationService {

    @Value("${security.authentification.jwt.token-validity-in-seconds:0}")
    private long tokenValidityInSeconds;

    @Value("${security.authentification.jwt.token-validity-in-seconds-for-remember-me:0}")
    private long tokenValidityInSecondsForRememberMe;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final IUserRepository userRepository;
    private final IPickerRepository pickerRepository;

    public AuthentificationService(AuthenticationManagerBuilder authenticationManagerBuilder, JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, IUserRepository userRepository, IPickerRepository pickerRepository) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.userRepository = userRepository;
        this.pickerRepository = pickerRepository;
    }

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


                String accessToken = createToken(authentication.getAuthorities(), user, login.rememberMe());
                String refreshToken = createRefreshToken(user.getEmail());
                Instant expiresToken = extractExpiresAt(accessToken);

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
        if(!isTokenExpired(refreshTokenRequestDTO.refreshToken())){

            String email = extractUsername(refreshTokenRequestDTO.refreshToken());

            User user = getByEmail(email);
            Collection<GrantedAuthority> authorities =  Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+ user.getRole().getRoleName()));

            String accessToken = createToken(authorities, user, false);
            String refreshToken = createRefreshToken(user.getEmail());
            Instant expiresToken = extractExpiresAt(accessToken);

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

    private String createToken(Collection<? extends GrantedAuthority> authorities, User user, boolean rememberMe)  {
        Optional<Picker> pickerOptional = pickerRepository.findById(user.getId());

        String scope = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        Instant now = Instant.now();
        Instant validity = now.plus(
                rememberMe ? this.tokenValidityInSecondsForRememberMe : this.tokenValidityInSeconds,
                ChronoUnit.SECONDS
        );

        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .id(UUID.randomUUID().toString())
                .issuedAt(now)
                .expiresAt(validity)
                .subject(user.getEmail())
                .claim(SecurityUtils.AUTHORITIES_KEY, scope)
                .claim(SecurityUtils.AUTHORITIES_USER_ID_KEY, user.getId());

        pickerOptional.ifPresent(picker ->
                claimsBuilder.claim(SecurityUtils.AUTHORITIES_PICKER_ID_KEY, picker.getId())
        );

        JwtClaimsSet claimsSet = claimsBuilder.build();

        JwsHeader jwsHeader = JwsHeader.with(SecurityUtils.JWT_ALGORITHM).build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claimsSet)).getTokenValue();
    }

    private String createRefreshToken(String email) {

        Instant instantNow = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .id(UUID.randomUUID().toString())
                .issuedAt(instantNow)
                .expiresAt(instantNow.plus(8, ChronoUnit.HOURS))
                .subject(email)
                .build();

        JwsHeader jwsHeader = JwsHeader.with(SecurityUtils.JWT_ALGORITHM).build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    private Instant extractExpiresAt(String token) {
        return jwtDecoder.decode(token).getExpiresAt();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiresAt(token) != null && extractExpiresAt(token).isBefore(Instant.now());
    }

    private String extractUsername(String token) {
        return jwtDecoder.decode(token).getSubject();
    }

    private User getByEmail(String email) throws MyUserNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(()-> new MyUserNotFoundException("user not found " + email));
    }

}
