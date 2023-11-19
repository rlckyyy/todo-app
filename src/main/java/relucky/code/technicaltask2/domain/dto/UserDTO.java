package relucky.code.technicaltask2.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import relucky.code.technicaltask2.common.enums.Role;

@AllArgsConstructor@NoArgsConstructor
@Getter@Setter
public class UserDTO {
    private String name;
    private String email;
    private Integer age;
    private Role role;
}
