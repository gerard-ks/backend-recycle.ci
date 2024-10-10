package ci.org.recycle.security;


import ci.org.recycle.models.Role;
import ci.org.recycle.models.User;
import ci.org.recycle.repositories.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserRepository userRepository;

    public CustomUserDetailsService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final Optional<User> user  = userRepository.findByEmail(username);

        if(user.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        Role role = user.get().getRole();

        List<Role> roleUserEntities = new ArrayList<>();

        if(role != null) {
            roleUserEntities.add(role);
        }

        final List<GrantedAuthority> grantedAuthorities = roleUserEntities.stream()
                .map(Role::getRoleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return user.map(userEntityRecover -> org.springframework.security.core.userdetails.User.builder()
                .username(userEntityRecover.getEmail())
                .password(userEntityRecover.getPassword())
                .authorities(grantedAuthorities)
                .build()).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}

