package relucky.code.technicaltask2.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import relucky.code.technicaltask2.domain.dto.TaskDTO;
import relucky.code.technicaltask2.domain.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;


    @PostMapping("/create")
    ResponseEntity<String> createTask(TaskDTO taskDTO){
        userService.createTask(taskDTO);
        return ResponseEntity.ok("Successfully created");
    }
    @DeleteMapping("/delete/{id}")
    ResponseEntity<String> deleteTask(@PathVariable Long id){
        userService.deleteTask(id);
        return ResponseEntity.ok("Successfully deleted");
    }
    @GetMapping
    ResponseEntity<?> getAllTask(){
        return ResponseEntity.ok(userService.findAllTask());
    }
    @GetMapping("/{id}")
    ResponseEntity<?> getOne(@PathVariable Long id){
        return ResponseEntity.ok(userService.findTask(id));
    }
}
