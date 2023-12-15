package relucky.code.technicaltask2.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import relucky.code.technicaltask2.common.enums.Role;

import java.util.Collection;
import java.util.List;


@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
@Builder
public class User implements UserDetails {
    @Id
    private String id;
    private String name;
    private String email;
    private Integer age;
    private String password;
    private Role role;
    @JsonIgnore
    private List<Task> taskList;

    public User(String name, String email, Integer age, String password, Role role) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var list = List.of(new SimpleGrantedAuthority("ROLE_".concat(role.name())));
        return list;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
