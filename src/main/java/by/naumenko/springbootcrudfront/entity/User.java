package by.naumenko.springbootcrudfront.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user", schema = "spring_boot")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Id.class)
    private Long id;

    @Column(unique = true)
    @JsonView(Views.Id.class)
    private String email;

    @JsonView(Views.IdFirstName.class)
    private String firstName;
    @JsonView(Views.IdLastName.class)
    private String lastName;
    @JsonView(Views.Age.class)
    private Byte age;
    @JsonView(Views.Password.class)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role", schema = "spring_boot", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonView(Views.Age.class)
    private Set<Role> authorities = new HashSet<>();

    @Transient
    @JsonView(Views.FullUser.class)
    private boolean isAccountNonExpired = true;
    @Transient
    @JsonView(Views.FullUser.class)
    private boolean isAccountNonLocked = true;
    @Transient
    @JsonView(Views.FullUser.class)
    private boolean isCredentialsNonExpired = true;
    @Transient
    @JsonView(Views.FullUser.class)
    private boolean isEnabled = true;


    @Override
    public String getUsername() {
        return email;
    }

    public boolean hasRole(String roleName) {
        if (authorities == null || authorities.size() == 0) {
            return false;
        }
        Optional<Role> finRole = authorities.stream()
                .filter(role -> (roleName).equals(role.getAuthority()))
                .findFirst();
        return finRole.isPresent();
    }
}
