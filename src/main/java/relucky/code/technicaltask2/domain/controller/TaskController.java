package relucky.code.technicaltask2.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import relucky.code.technicaltask2.domain.dto.TaskDTO;
import relucky.code.technicaltask2.domain.service.MinioService;
import relucky.code.technicaltask2.domain.service.TaskService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/task")
public class TaskController {
    private final TaskService taskService;
    private final MinioService minioService;
    @PostMapping("/create")
    ResponseEntity<?> createTask(@RequestBody TaskDTO taskDTO){
        return ResponseEntity.ok(taskService.createTask(taskDTO));
    }
    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteTask(@PathVariable Long id){
        return ResponseEntity.ok(taskService.deleteTask(id));
    }
    @GetMapping
    ResponseEntity<?> getAllTask(){
        return ResponseEntity.ok(taskService.findAllTask());
    }
    @GetMapping("/{id}")
    ResponseEntity<?> getOne(@PathVariable Long id){
        return ResponseEntity.ok(taskService.findTask(id));
    }
    @PostMapping("/upload/{id}")
    ResponseEntity<?> uploadFileToTask(@PathVariable Long id, @RequestParam("file") MultipartFile multipartFile){
        return ResponseEntity.ok(minioService.uploadFile(multipartFile,id));
    }
    @DeleteMapping("/{taskId}/files/{fileId}")
    ResponseEntity<?> deleteFileFromTask(@PathVariable("taskId") Long taskId, @PathVariable("fileId") Long fileId){
        return ResponseEntity.ok(minioService.deleteFile(fileId, taskId));
    }
}
