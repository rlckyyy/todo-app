package relucky.code.technicaltask2.domain.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import relucky.code.technicaltask2.domain.dto.FileDTO;
import relucky.code.technicaltask2.domain.dto.TaskDTO;
import relucky.code.technicaltask2.domain.service.MinioService;
import relucky.code.technicaltask2.domain.service.TaskService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/task")
public class TaskController {
    private final TaskService taskService;
    private final MinioService minioService;
    @PostMapping("/create")
    TaskDTO createTask(@RequestBody TaskDTO taskDTO){
        return taskService.createTask(taskDTO);
    }
    @DeleteMapping("/delete/{id}")
    TaskDTO deleteTask(@PathVariable String id){
        return taskService.deleteTask(id);
    }
    @GetMapping
    List<TaskDTO> getAllTask(){
        return taskService.findAllTask();
    }
    @GetMapping("{id}")
    TaskDTO getOne(@PathVariable String id){
        return taskService.findTask(id);
    }
    @PostMapping("/upload/{id}")
    FileDTO uploadFileToTask(@PathVariable String id, @RequestParam("file") MultipartFile multipartFile){
        return minioService.uploadFile(multipartFile,id);
    }
    @DeleteMapping("/{taskId}/files/{fileId}")
    FileDTO deleteFileFromTask(@PathVariable("taskId") String taskId, @PathVariable("fileId") String fileId){
        return minioService.deleteFile(fileId, taskId);
    }
}
