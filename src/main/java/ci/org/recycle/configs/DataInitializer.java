package ci.org.recycle.configs;


import ci.org.recycle.models.Role;
import ci.org.recycle.models.User;
import ci.org.recycle.repositories.IRoleRepository;
import ci.org.recycle.repositories.IUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(IUserRepository userRepository, IRoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setRoleName("ADMINISTRATEUR");
            roleRepository.save(adminRole);

            Role citoyenRole = new Role();
            citoyenRole.setRoleName("CITOYEN");
            roleRepository.save(citoyenRole);


            Role agentPointRole = new Role();
            agentPointRole.setRoleName("AGENT_POINT_COLLECTE");
            roleRepository.save(agentPointRole);


            Role reparateurRole = new Role();
            reparateurRole.setRoleName("REPARATEUR");
            roleRepository.save(reparateurRole);
        }

        if (userRepository.count() == 0) {
            User adminUser = new User();
            adminUser.setFirstName("admin");
            adminUser.setLastName("admin");
            adminUser.setBirthDate(LocalDate.of(2000, 1, 1));
            adminUser.setTelephone("002250100010001");
            adminUser.setEmail("admin@mail.ci");
            adminUser.setPassword(passwordEncoder.encode("12345678"));

            Optional<Role> adminRole = roleRepository.findByRoleName("ADMINISTRATEUR");
            adminRole.ifPresent(adminUser::setRole);

            userRepository.save(adminUser);
        }
    }
}
