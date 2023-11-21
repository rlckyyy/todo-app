package relucky.code.technicaltask2.domain.service.impl;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import relucky.code.technicaltask2.common.exception.TaskNotFoundException;
import relucky.code.technicaltask2.common.exception.UnauthorizedTaskAccessException;
import relucky.code.technicaltask2.common.exception.UserNotFoundException;
import relucky.code.technicaltask2.domain.entity.File;
import relucky.code.technicaltask2.domain.entity.Task;
import relucky.code.technicaltask2.domain.entity.User;
import relucky.code.technicaltask2.domain.repository.FileRepository;
import relucky.code.technicaltask2.domain.repository.TaskRepository;
import relucky.code.technicaltask2.domain.repository.UserRepository;
import relucky.code.technicaltask2.domain.service.MinioService;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {
    private final MinioClient minioClient;
    private final FileRepository fileRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;


    @Override
    public String uploadFileToMinio(MultipartFile file) {
        try {
            String bucketName = "file-system";
            String objectName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();
            minioClient.putObject(putObjectArgs);
            return bucketName + "/" + objectName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file to MINIO", e);
        }
    }

    @Override
    public File uploadFile(MultipartFile file, Long taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        User currentUser = getUser();
        if (taskOptional.isPresent()){
            Task task = taskOptional.get();
            if (currentUser.getTaskList().contains(task)){
                try {
                    String minioPath = uploadFileToMinio(file);
                    File fileEntity = File.builder()
                            .fileName(file.getOriginalFilename())
                            .minioPath(minioPath)
                            .fileType(file.getContentType())
                            .task(task)
                            .build();

                    return fileRepository.save(fileEntity);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to upload file", e);
                }
            } else {
                throw new UnauthorizedTaskAccessException("User does not have access to task with id: " + taskId);
            }
        } else {
            throw new TaskNotFoundException("Task with id: " + taskId + " does not exist");
        }
    }
    private User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        System.out.println(email);
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
