package relucky.code.technicaltask2.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import relucky.code.technicaltask2.common.enums.Role;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
@Table(name = "users")
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}
