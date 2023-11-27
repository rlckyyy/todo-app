package relucky.code.technicaltask2.domain.service.impl;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import relucky.code.technicaltask2.common.exception.CFileNotFoundException;
import relucky.code.technicaltask2.common.exception.TaskNotFoundException;
import relucky.code.technicaltask2.common.exception.UnauthorizedAccessException;
import relucky.code.technicaltask2.common.exception.UserNotFoundException;
import relucky.code.technicaltask2.domain.dto.FileDTO;
import relucky.code.technicaltask2.domain.entity.File;
import relucky.code.technicaltask2.domain.entity.Task;
import relucky.code.technicaltask2.domain.entity.User;
import relucky.code.technicaltask2.domain.mapper.FileMapper;
import relucky.code.technicaltask2.domain.repository.FileRepository;
import relucky.code.technicaltask2.domain.repository.TaskRepository;
import relucky.code.technicaltask2.domain.repository.UserRepository;
import relucky.code.technicaltask2.domain.service.MinioService;

import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {
    private final MinioClient minioClient;
    private final FileRepository fileRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final FileMapper fileMapper;


    @Override
    public String uploadFileToMinio(MultipartFile file) {
        try {
            String bucketName = "file-system";
            String objectName = UUID.randomUUID() + "_" + file.getOriginalFilename();
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
                throw new UnauthorizedAccessException("User does not have access to task with id: " + taskId);
            }
        } else {
            throw new TaskNotFoundException("Task with id: " + taskId + " does not exist");
        }
    }

    @Override
    public FileDTO deleteFile(Long fileId, Long taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        User currentUser = getUser();
        if (taskOptional.isPresent()){
            Task task = taskOptional.get();
            if (currentUser.getTaskList().contains(task)){
                Optional<File> fileOptional = fileRepository.findById(fileId);
                if (fileOptional.isPresent()){
                    File file = fileOptional.get();
                    if (file.getTask().equals(task)){
                        deleteFileFromMinio(file.getMinioPath());
                        task.getFileList().remove(file);
                        fileRepository.deleteById(fileId);
                        return fileMapper.toDTO(file);
                    } else {
                        throw new UnauthorizedAccessException("User does not have access to file with id: " + fileId);
                    }
                } else {
                    throw new CFileNotFoundException("File with id " + fileId + " not found");
                }
            } else {
                throw new UnauthorizedAccessException("User does not have access to task with id: " + taskId);
            }
        } else {
            throw new TaskNotFoundException("Task with id " + taskId + " not found");
        }
    }

    private void deleteFileFromMinio(String minioPath){
        try {
            String[] parts = minioPath.split("/");
            String bucketName = parts[0];
            String objectName = parts[1];
            minioClient.removeObject(
                    RemoveObjectArgs
                            .builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete file from MINIO: " + e);
        }
    }

    private User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
