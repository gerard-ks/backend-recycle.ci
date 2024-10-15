package ci.org.recycle.services.impl;

import ci.org.recycle.models.*;
import ci.org.recycle.repositories.*;
import ci.org.recycle.services.IUserService;
import ci.org.recycle.services.dtos.requests.*;
import ci.org.recycle.services.dtos.responses.*;
import ci.org.recycle.services.mappers.*;
import ci.org.recycle.web.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final ICitizenRepository citizenRepository;
    private final IPickerRepository pickerRepository;
    private final IAddressRepository addressRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ICitizenMapper citizenMapper;
    private final IPickerMapper pickerMapper;
    private final ICollectionPointRepository collectionPointRepository;
    private final IScheduleRepository scheduleRepository;
    private final IScheduleMapper scheduleMapper;



    @Override
    public CitizenResponseDTO createAccountCitizen(CitizenRequestDTO citizenRequestDTO) throws MyRoleNotFoundException {

        Role role = roleRepository.findByRoleName("CITOYEN").orElseThrow(() -> new MyRoleNotFoundException("role not found exception"));

        Address saveAddress = getSaveAddress(citizenRequestDTO);
        User saveUser = getSaveUserCitizen(citizenRequestDTO, role, saveAddress);
        Citizen saveCitizen = getSaveCitizen(citizenRequestDTO, saveUser);

        return citizenMapper.toDto(saveCitizen);
    }

    private Citizen getSaveCitizen(CitizenRequestDTO citizenRequestDTO, User saveUser) {
        Citizen citizen = new Citizen();
        citizen.setCitizenType(citizenRequestDTO.citizenType());
        citizen.setUser(saveUser);
        return citizenRepository.save(citizen);
    }

    private User getSaveUserCitizen(CitizenRequestDTO citizenRequestDTO, Role role, Address saveAddress) {
        User user =  new User();
        user.setFirstName(citizenRequestDTO.userRequestDTO().firstName());
        user.setLastName(citizenRequestDTO.userRequestDTO().lastName());
        user.setEmail(citizenRequestDTO.userRequestDTO().email());
        user.setBirthDate(citizenRequestDTO.userRequestDTO().birthDate());
        user.setTelephone(citizenRequestDTO.userRequestDTO().telephone());
        user.setEmail(citizenRequestDTO.userRequestDTO().email());
        user.setPassword(passwordEncoder.encode(citizenRequestDTO.userRequestDTO().password()));
        user.setRole(role);
        user.setAddress(saveAddress);
        return userRepository.save(user);
    }

    private Address getSaveAddress(CitizenRequestDTO citizenRequestDTO) {
        Address address = new Address();
        address.setTown(citizenRequestDTO.userRequestDTO().addressRequestDTO().town());
        address.setCity(citizenRequestDTO.userRequestDTO().addressRequestDTO().city());
        address.setDistrict(citizenRequestDTO.userRequestDTO().addressRequestDTO().district());
        return addressRepository.save(address);
    }


    @Override
    public CitizenResponseDTO updateAccountCitizen(CitizenRequestUpdateDTO citizenRequestUpdateDTO) throws CitizenNotFoundException {

        Citizen citizen = citizenRepository.findById(citizenRequestUpdateDTO.citizenId()).orElseThrow(() -> new CitizenNotFoundException("citizen not found exception"));

        if(citizenRequestUpdateDTO.pointDeRecompense() > 0) {
            Long oldPoint = citizen.getLoyaltyPoint();
            Long newPoint = oldPoint + citizenRequestUpdateDTO.pointDeRecompense();
            citizen.setLoyaltyPoint(newPoint);
        }

        updateUserAccount(citizen.getUser(), citizenRequestUpdateDTO.userRequestUpdateDTO());

        citizenRepository.save(citizen);

        return citizenMapper.toDto(citizen);
    }


    @Override
    public CollectionPointResponsesDTO listCollectionPoint(int pageNo, int pageSize, String sortBy, String sortDir, Double latitude, Double longitude) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<CollectionPoint> collectionPoints;
        if (latitude != null && longitude != null) {
            collectionPoints = collectionPointRepository.findNearestCollectionPoints(latitude, longitude, pageable);
        } else {
            collectionPoints = collectionPointRepository.findAll(pageable);
        }

        List<CollectionPoint> allCollectionPoints = collectionPoints.getContent();
        List<AllCollectionPointResponseDTO> dtos = allCollectionPoints.stream().map(this::getDtoCollectionPoint).toList();

        return new CollectionPointResponsesDTO(
                dtos,
                collectionPoints.getNumber() + 1,
                collectionPoints.getSize(),
                collectionPoints.getTotalElements(),
                collectionPoints.isLast(),
                collectionPoints.getTotalPages()
        );
    }

    @Override
    public CurrentUserResponseDTO getCurrentUser() throws MyUserNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new MyUserNotFoundException("No authentication found");
        }

        String username = extractUsername(authentication);
        if (username == null) {
            throw new MyUserNotFoundException("Unable to extract username from authentication");
        }

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new MyUserNotFoundException("User not found with email: " + username));

        return new CurrentUserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }

    private String extractUsername(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getSubject();
        } else if (authentication.getPrincipal() instanceof String s) {
            return s;
        }
        return null;
    }

    private AllCollectionPointResponseDTO getDtoCollectionPoint(CollectionPoint collectionPoint) {

        List<Schedule> schedules = scheduleRepository.findByCollectionPoint_Id(collectionPoint.getId());
        List<Picker> pickers = pickerRepository.findByCollectionPoint_Id(collectionPoint.getId());

        List<ScheduleResponseDTO> scheduleResponseDTOS = new ArrayList<>();
        List<PickerResponseDTO> pickerResponseDTOS = new ArrayList<>();

        for(Schedule schedule: schedules) {
            scheduleResponseDTOS.add(scheduleMapper.toDto(schedule));
        }

        for(Picker picker: pickers) {
            pickerResponseDTOS.add(pickerMapper.toDto(picker));
        }

        return new AllCollectionPointResponseDTO(
            collectionPoint.getId(),
            collectionPoint.getDescription(),
            collectionPoint.getLatitude(),
            collectionPoint.getLongitude(),
            !pickerResponseDTOS.isEmpty() ? pickerResponseDTOS : null,
            !scheduleResponseDTOS.isEmpty() ? scheduleResponseDTOS : null
        );
    }


    private void updateUserAccount(User user, UserRequestUpdateDTO requestDTO) {
        if (requestDTO.password() != null && !requestDTO.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(requestDTO.password()));
        }

        if (requestDTO.enabled() != null) {
            user.setEnabled(requestDTO.enabled());
        }

        if (requestDTO.accountNonLocked() != null) {
            user.setAccountNonLocked(requestDTO.accountNonLocked());
        }

        userRepository.save(user);
    }
}
