package by.naumenko.springbootcrudfront.repo;

import by.naumenko.springbootcrudfront.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getByAuthority(String authority);
}
