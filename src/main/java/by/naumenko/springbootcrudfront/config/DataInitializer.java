package by.naumenko.springbootcrudfront.config;

import by.naumenko.springbootcrudfront.entity.Role;
import by.naumenko.springbootcrudfront.entity.User;
import by.naumenko.springbootcrudfront.repo.UserRepository;
import by.naumenko.springbootcrudfront.servise.RoleService;
import by.naumenko.springbootcrudfront.servise.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
@AllArgsConstructor
public class DataInitializer {

    private RoleService roleService;

    private UserService userService;

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
                .password("admin")
                .authorities(Set.of(roleService.getByAuthority("ROLE_ADMIN"),roleService.getByAuthority("ROLE_USER")))
                .build();

        userService.saveUser(userAdmin);
        User userUser= User.builder()
                .email("user@mail.ru")
                .firstName("user")
                .lastName("user")
                .age((byte) 12)
                .password("user")
                .authorities(Set.of(roleService.getByAuthority("ROLE_USER")))
                .build();

        userService.saveUser(userUser);
    }
}
