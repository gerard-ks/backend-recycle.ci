package ci.org.recycle;

import ci.org.recycle.models.Role;
import ci.org.recycle.models.User;
import ci.org.recycle.repositories.IRoleRepository;
import ci.org.recycle.repositories.IUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
public class RecycleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecycleApplication.class, args);
	}


	/*@Bean
	CommandLineRunner commandLineRunner(IUserRepository userRepository, IRoleRepository roleRepository,PasswordEncoder passwordEncoder) {
		return args -> {

			Role role1 = new Role();
			role1.setRoleName("ADMINISTRATEUR");
			roleRepository.save(role1);

			Role role2 = new Role();
			role2.setRoleName("AGENT_POINT_COLLECTE");
			roleRepository.save(role2);

			Role role3 = new Role();
			role3.setRoleName("CITOYEN");
			roleRepository.save(role3);


			Role role4 = new Role();
			role4.setRoleName("REPARATEUR");
			roleRepository.save(role4);

			User user = new User();
			user.setFirstName("admin");
			user.setEmail("admin@mail.ci");
			user.setPassword(passwordEncoder.encode("12345678"));
			user.setRole(role1);
			userRepository.save(user);

		};
	}*/

}

