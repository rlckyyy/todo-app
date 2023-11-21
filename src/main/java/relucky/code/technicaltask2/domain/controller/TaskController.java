package relucky.code.technicaltask2.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import relucky.code.technicaltask2.domain.dto.TaskDTO;
import relucky.code.technicaltask2.domain.service.TaskService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/task")
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/create")
    ResponseEntity<String> createTask(@RequestBody TaskDTO taskDTO){
        taskService.createTask(taskDTO);
        return ResponseEntity.ok("Successfully created");
    }
    @DeleteMapping("/delete/{id}")
    ResponseEntity<String> deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return ResponseEntity.ok("Successfully deleted");
    }
    @GetMapping
    ResponseEntity<?> getAllTask(){
        return ResponseEntity.ok(taskService.findAllTask());
    }
    @GetMapping("/{id}")
    ResponseEntity<?> getOne(@PathVariable Long id){
        return ResponseEntity.ok(taskService.findTask(id));
    }
}
