package relucky.code.technicaltask2.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import relucky.code.technicaltask2.domain.dto.UserDTO;
import relucky.code.technicaltask2.domain.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final UserService userService;
    @DeleteMapping("/{id}")
    UserDTO deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }
    @GetMapping
    List<UserDTO> getAllUser(){
        return userService.findAll();
    }

    @GetMapping("/{id}")
    UserDTO getUserInfo(@PathVariable Long id){
        return userService.findOne(id);
    }
}
