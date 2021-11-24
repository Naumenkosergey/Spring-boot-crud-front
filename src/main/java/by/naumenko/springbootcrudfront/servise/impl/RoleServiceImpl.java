package by.naumenko.springbootcrudfront.servise.impl;

import by.naumenko.springbootcrudfront.entity.Role;
import by.naumenko.springbootcrudfront.entity.User;
import by.naumenko.springbootcrudfront.repo.RoleRepository;
import by.naumenko.springbootcrudfront.servise.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> findAllRole() {
        return roleRepository.findAll();
    }


    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    private Role findRoleByAuthority(String authority) {
        return findAllRole().stream()
                .filter(role -> authority.equals(role.getAuthority()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(String.format("Role %s not found", authority)));
    }

    @Override
    public void instRole(User user) {
        for (Role role : user.getAuthorities()) {
            try {
                role.setId(findRoleByAuthority(role.getAuthority()).getId());
            } catch (NoSuchElementException ex) {
                ex.printStackTrace();
            }
        }

    }

    @Override
    public Set<Role> addRoles(String... authorities) {
        Set<Role> roles = new HashSet<>();
        for (String authority : authorities) {
            Role role = new Role();
            role.setAuthority(authority);
            roles.add(role);
        }
        return roles;
    }

    @Override
    public Role getByAuthority(String authority) {
       return roleRepository.getByAuthority(authority);
    }
}
