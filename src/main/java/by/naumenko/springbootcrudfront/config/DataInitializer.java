package by.naumenko.springbootcrudfront.config;

import by.naumenko.springbootcrudfront.entity.Role;
import by.naumenko.springbootcrudfront.entity.User;
import by.naumenko.springbootcrudfront.repo.UserRepository;
import by.naumenko.springbootcrudfront.servise.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@AllArgsConstructor
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;
    private RoleService roleService;

    private UserRepository userRepository;

    @PostConstruct
    public void initData() {
        Role admin = new Role();
        admin.setAuthority("ROLE_ADMIN");
        Role user = new Role();
        user.setAuthority("ROLE_USER");
        roleService.saveRole(admin);
        roleService.saveRole(user);

        User userAdmin= User.builder()
                .email("admin@mail.ru")
                .firstName("Сергей")
                .lastName("Науменко")
                .age((byte) 28)
                .password(passwordEncoder.encode("admin"))
                .authorities(roleService.addRoles("ROLE_ADMIN","ROLE_USER"))
                .build();

        roleService.instRole(userAdmin);
        userRepository.save(userAdmin);
        User userUser= User.builder()
                .email("user@mail.ru")
                .firstName("user")
                .lastName("user")
                .age((byte) 12)
                .password(passwordEncoder.encode("user"))
                .authorities(roleService.addRoles("ROLE_USER"))
                .build();

        roleService.instRole(userUser);
        userRepository.save(userUser);
    }
}
