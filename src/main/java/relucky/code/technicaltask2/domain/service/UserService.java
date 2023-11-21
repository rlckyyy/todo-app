package relucky.code.technicaltask2.domain.service;

import relucky.code.technicaltask2.domain.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO deleteUser(Long id);
    List<UserDTO> findAll();
    UserDTO findOne(Long id);
}
