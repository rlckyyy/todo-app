package relucky.code.technicaltask2.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import relucky.code.technicaltask2.domain.dto.UserDTO;
import relucky.code.technicaltask2.domain.entity.User;


@Mapper
public interface UserMapper {
    User toModel(UserDTO userDTO);
    UserDTO toDTO(User user);
}
