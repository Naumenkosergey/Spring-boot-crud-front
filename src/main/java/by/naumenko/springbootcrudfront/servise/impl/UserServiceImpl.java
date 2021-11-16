package by.naumenko.springbootcrudfront.servise.impl;

import by.naumenko.springbootcrudfront.entity.User;
import by.naumenko.springbootcrudfront.repo.UserRepository;
import by.naumenko.springbootcrudfront.servise.RoleService;
import by.naumenko.springbootcrudfront.servise.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email.toLowerCase()).orElseThrow(() ->
                new UsernameNotFoundException("user not found"));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public void removeUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User saveUser(User user) {
        String oldPassword = "";
        if (user.getId() != null && getUser(user.getId()) != null) {
            oldPassword = getUser(user.getId()).getPassword();
        }
        user.setPassword(user.getPassword() != "" ?
                passwordEncoder.encode(user.getPassword()) :
                oldPassword);
        roleService.instRole(user);
        return userRepository.save(user);
    }

}
