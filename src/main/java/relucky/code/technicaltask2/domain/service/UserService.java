package relucky.code.technicaltask2.domain.service;

import relucky.code.technicaltask2.domain.dto.UserDTO;
import relucky.code.technicaltask2.domain.entity.User;

import java.util.List;

public interface UserService {
    UserDTO deleteUser(String id);
    List<UserDTO> findAll();
    UserDTO findOne(String id);
    User getUser();
}
