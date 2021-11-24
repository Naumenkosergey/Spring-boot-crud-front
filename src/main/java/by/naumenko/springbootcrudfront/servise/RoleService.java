package by.naumenko.springbootcrudfront.servise;

import by.naumenko.springbootcrudfront.entity.Role;
import by.naumenko.springbootcrudfront.entity.User;

import java.util.List;
import java.util.Set;

public interface RoleService {

    List<Role> findAllRole();

    void instRole(User user);

    Role saveRole(Role role);

    Set<Role> addRoles(String...authorities);

    Role getByAuthority(String authority);
}
