package relucky.code.technicaltask2.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import relucky.code.technicaltask2.domain.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final UserService userService;
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }
    @GetMapping
    ResponseEntity<?> getAllUser(){
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getUserInfo(@PathVariable Long id){
        return ResponseEntity.ok(userService.findOne(id));
    }
}
