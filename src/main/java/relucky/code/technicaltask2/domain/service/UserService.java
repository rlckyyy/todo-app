package relucky.code.technicaltask2.domain.service;

import relucky.code.technicaltask2.domain.dto.UserDTO;
import relucky.code.technicaltask2.domain.entity.User;

import java.util.List;

public interface UserService {
    UserDTO deleteUser(Long id);
    List<UserDTO> findAll();
    UserDTO findOne(Long id);
    User getUser();
}
