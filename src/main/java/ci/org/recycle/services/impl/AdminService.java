package ci.org.recycle.services.impl;

import ci.org.recycle.models.*;
import ci.org.recycle.repositories.*;
import ci.org.recycle.services.IAdminService;
import ci.org.recycle.services.dtos.requests.*;
import ci.org.recycle.services.dtos.responses.*;
import ci.org.recycle.services.mappers.*;
import ci.org.recycle.web.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService implements IAdminService {

    private final ITypeWasteRepository typeWasteRepository;
    private final ITypeWasteMapper typeWasteMapper;
    private final IRoleRepository roleRepository;
    private final IRepairerMapper repairerMapper;
    private final IRepairerRepository repairerRepository;
    private final IAddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final IUserRepository userRepository;
    private final IPickerMapper pickerMapper;
    private final ICollectionPointRepository collectionPointRepository;
    private final IPickerRepository pickerRepository;
    private final ICitizenRepository citizenRepository;
    private final ICitizenMapper citizenMapper;
    private final IScheduleMapper scheduleMapper;
    private final IScheduleRepository scheduleRepository;
    private final ICollectionPointMapper collectionPointMapper;

    @Override
    public TypeWasteResponseDTO createTypeWaste(TypeWasteRequestDTO typeWasteRequestDTO) {
        TypeWaste typeWaste = new TypeWaste();
        typeWaste.setDescription(typeWasteRequestDTO.description());
        TypeWaste saved = typeWasteRepository.save(typeWaste);
        return typeWasteMapper.toDto(saved);
    }

    @Override
    public List<TypeWasteResponseDTO> getAllTypeWaste() {
        return typeWasteRepository.findAll().stream()
                .map(typeWasteMapper::toDto)
                .toList();
    }

    @Override
    public void deleteTypeWaste(UUID id) throws TypeWasteNotFoundException {
      TypeWaste typeWaste = typeWasteRepository.findById(id).orElseThrow(() -> new TypeWasteNotFoundException("type waste not found"));
      typeWasteRepository.delete(typeWaste);
    }

    @Override
    public RepairerResponseDTO createAccountRepairer(RepairerRequestDTO repairerRequestDTO) throws MyRoleNotFoundException {
        Role role = roleRepository.findByRoleName("REPARATEUR").orElseThrow(()-> new MyRoleNotFoundException("role not found exception"));
        Address savedAddress = getAddressRepairer(repairerRequestDTO);
        User saveUser = saveUser(repairerRequestDTO.userRequestDTO(), role, savedAddress);
        Repairer saveRepairer = getSaveRepairer(repairerRequestDTO, saveUser);
        return repairerMapper.toDto(saveRepairer);
    }

    private Repairer getSaveRepairer(RepairerRequestDTO repairerRequestDTO, User saveUser) {
        Repairer repairer = new Repairer();
        repairer.setCertificate(repairerRequestDTO.certificate());
        repairer.setSpeciality(repairerRequestDTO.speciality());
        repairer.setYearOfExperience(repairerRequestDTO.yearOfExperience());
        repairer.setUser(saveUser);
        return repairerRepository.save(repairer);
    }

    private Address getAddressRepairer(RepairerRequestDTO repairerRequestDTO) {
        Address address = new Address();
        address.setDistrict(repairerRequestDTO.userRequestDTO().addressRequestDTO().district());
        address.setTown(repairerRequestDTO.userRequestDTO().addressRequestDTO().town());
        address.setCity(repairerRequestDTO.userRequestDTO().addressRequestDTO().city());
        return addressRepository.save(address);
    }

    @Override
    public RepairerResponsesDTO listAccountRepairer(int pageNo, int pageSize, String sortBy, String sortDir, String querySearch) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Repairer> repairers;
        if (querySearch != null && !querySearch.isEmpty()) {
            repairers = repairerRepository.findBySearchQuery(querySearch, pageable);
        } else {
            repairers = repairerRepository.findAll(pageable);
        }


        List<Repairer> allRepairers = repairers.getContent();
        List<RepairerResponseDTO> dtos = allRepairers.stream().map(repairerMapper::toDto).toList();


        return new RepairerResponsesDTO(
                dtos,
                repairers.getNumber() + 1,
                repairers.getSize(),
                repairers.getTotalElements(),
                repairers.isLast(),
                repairers.getTotalPages()
        );
    }


    @Override
    public RepairerResponseDTO updateAccountRepairer(RepairerRequestUpdateDTO repairerRequestUpdateDTO) throws RepairerNotFoundException {

        Repairer repairer = repairerRepository.findById(repairerRequestUpdateDTO.repairerId()).orElseThrow(() -> new RepairerNotFoundException("citizen not found exception"));

        updateUserAccount(repairer.getUser(), repairerRequestUpdateDTO.userRequestUpdateDTO());

        repairerRepository.save(repairer);

        return repairerMapper.toDto(repairer);
    }

    @Override
    public PickerResponseDTO createAccountPicker(PickerRequestDTO pickerRequestDTO) throws MyRoleNotFoundException, CollectionPointNotFoundException {

        Role role = roleRepository.findByRoleName("AGENT_POINT_COLLECTE").orElseThrow(()-> new MyRoleNotFoundException("role not found exception"));
        CollectionPoint collectionPoint = collectionPointRepository.findById(pickerRequestDTO.collectionPoint_id()).orElseThrow(()-> new CollectionPointNotFoundException("collection point not found exception"));

        Address saveAdress = getSaveAdress(pickerRequestDTO);
        User saveUser = saveUser(pickerRequestDTO.userRequestDTO(), role, saveAdress);

        Picker picker = new Picker();
        picker.setSerialNumber(RandomStringUtils.secure().nextNumeric(8));
        picker.setCollectionPoint(collectionPoint);
        picker.setUser(saveUser);

        Picker savedPicker = pickerRepository.save(picker);

        return pickerMapper.toDto(savedPicker);
    }

    private Address getSaveAdress(PickerRequestDTO pickerRequestDTO) {
        Address address = new Address();
        address.setTown(pickerRequestDTO.userRequestDTO().addressRequestDTO().town());
        address.setCity(pickerRequestDTO.userRequestDTO().addressRequestDTO().city());
        address.setDistrict(pickerRequestDTO.userRequestDTO().addressRequestDTO().district());
        return addressRepository.save(address);
    }

    @Override
    public PickerResponsesDTO listAccountPicker(int pageNo, int pageSize, String sortBy, String sortDir, String querySearch) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Picker> pickers;
        if (querySearch != null && !querySearch.isEmpty()) {
            pickers = pickerRepository.findBySearchQuery(querySearch, pageable);
        } else {
            pickers = pickerRepository.findAll(pageable);
        }

        List<Picker> allPickers = pickers.getContent();
        List<PickerResponseDTO> dtos = allPickers.stream().map(pickerMapper::toDto).toList();

        return new PickerResponsesDTO(
                dtos,
                pickers.getNumber() + 1,
                pickers.getSize(),
                pickers.getTotalElements(),
                pickers.isLast(),
                pickers.getTotalPages()
        );
    }

    @Override
    public PickerResponseDTO updateAccountPicker(PickerRequestUpdateDTO pickerRequestUpdateDTO) throws PickerNotFoundException {

        Picker picker = pickerRepository.findById(pickerRequestUpdateDTO.pickerId()).orElseThrow(() -> new PickerNotFoundException("picker not found"));

        updateUserAccount(picker.getUser(), pickerRequestUpdateDTO.userRequestUpdateDTO());

        pickerRepository.save(picker);

        return pickerMapper.toDto(picker);
    }


    @Override
    public CitizenResponsesDTO listAccountCitizen(int pageNo, int pageSize, String sortBy, String sortDir, String querySearch) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Citizen> citizens;
        if (querySearch != null && !querySearch.isEmpty()) {
            citizens = citizenRepository.findBySearchQuery(querySearch, pageable);
        } else {
            citizens = citizenRepository.findAll(pageable);
        }

        List<Citizen> allCitizens = citizens.getContent();
        List<CitizenResponseDTO> dtos = allCitizens.stream().map(citizenMapper::toDto).toList();

        return new CitizenResponsesDTO(
                dtos,
                citizens.getNumber() + 1,
                citizens.getSize(),
                citizens.getTotalElements(),
                citizens.isLast(),
                citizens.getTotalPages()
        );
    }


    @Override
    public AllCollectionPointResponseDTO createCollectionPoint(CollectionPointRequestDTO collectionPointRequestDTO) {

        CollectionPoint savePoint = getCollectionPoint(collectionPointRequestDTO);
        List<Schedule> schedules = new ArrayList<>();
        List<ScheduleResponseDTO> scheduleResponseDTOS = new ArrayList<>();

        for(ScheduleRequestDTO scheduleRequestDTO: collectionPointRequestDTO.scheduleRequestDTOList()) {
            schedules.add(saveSchedules(scheduleRequestDTO, savePoint));
        }

        for(Schedule schedule: schedules) {
            scheduleResponseDTOS.add(scheduleMapper.toDto(schedule));
        }


        return new AllCollectionPointResponseDTO(
                savePoint.getId(),
                savePoint.getDescription(),
                savePoint.getLatitude(),
                savePoint.getLongitude(),
                null,
                scheduleResponseDTOS
        );
    }

    @Override
    public AllCollectionPointResponseDTO updateCollectionPoint(CollectionPointRequestUpdateDTO collectionPointRequestUpdateDTO) throws CollectionPointNotFoundException {

        CollectionPoint collectionPoint = collectionPointRepository.findById(collectionPointRequestUpdateDTO.collectionPointId()).orElseThrow(() -> new CollectionPointNotFoundException("collection point not found"));

        if(collectionPointRequestUpdateDTO.latitude() != null ) {
            collectionPoint.setLatitude(collectionPointRequestUpdateDTO.latitude());
        }

        if(collectionPointRequestUpdateDTO.longitude() != null ) {
            collectionPoint.setLongitude(collectionPointRequestUpdateDTO.longitude());
        }

        collectionPointRepository.save(collectionPoint);

        return collectionPointMapper.toDto(collectionPoint);
    }

    private CollectionPoint getCollectionPoint(CollectionPointRequestDTO collectionPointRequestDTO) {
        CollectionPoint collectionPoint = new CollectionPoint();
        collectionPoint.setDescription(collectionPointRequestDTO.description());
        collectionPoint.setLatitude(collectionPointRequestDTO.latitude());
        collectionPoint.setLongitude(collectionPointRequestDTO.longitude());
        return collectionPointRepository.save(collectionPoint);
    }

    private Schedule saveSchedules(ScheduleRequestDTO scheduleRequestDTO, CollectionPoint savePoint) {
        Schedule schedule = new Schedule();
        schedule.setDay(scheduleRequestDTO.day());
        schedule.setStartTime(scheduleRequestDTO.startTime());
        schedule.setEndTime(scheduleRequestDTO.endTime());
        schedule.setCollectionPoint(savePoint);
        return scheduleRepository.save(schedule);
    }

    private User saveUser(UserRequestDTO userRequestDTO, Role role, Address address) {
        User user = new User();
        user.setFirstName(userRequestDTO.firstName());
        user.setLastName(userRequestDTO.lastName());
        user.setEmail(userRequestDTO.email());
        user.setTelephone(userRequestDTO.telephone());
        user.setBirthDate(userRequestDTO.birthDate());
        user.setPassword(passwordEncoder.encode(userRequestDTO.password()));
        user.setRole(role);
        user.setAddress(address);
        return userRepository.save(user);
    }

    private <T extends UserRequestUpdateDTO> void updateUserAccount(User user, T requestDTO) {
        if (requestDTO.password() != null) {
            user.setPassword(passwordEncoder.encode(requestDTO.password()));
        }

        if (requestDTO.enabled()) {
            user.setEnabled(true);
        }

        if (requestDTO.accountNonLocked()) {
            user.setAccountNonLocked(true);
        }

        userRepository.save(user);
    }
}
