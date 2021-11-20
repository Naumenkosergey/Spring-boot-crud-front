package by.naumenko.springbootcrudfront.servise;

import by.naumenko.springbootcrudfront.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDetails loadUserByUsername(String email);

    List<User> findAll();

    User getUser(Long id);

    void removeUser(Long id);
    void removeUser(User user);

    User saveUser(User user);

}
