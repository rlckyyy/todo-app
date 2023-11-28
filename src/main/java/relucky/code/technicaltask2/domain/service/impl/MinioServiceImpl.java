package relucky.code.technicaltask2.domain.service.impl;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import relucky.code.technicaltask2.common.exception.CFileNotFoundException;
import relucky.code.technicaltask2.common.exception.TaskNotFoundException;
import relucky.code.technicaltask2.common.exception.UnauthorizedAccessException;
import relucky.code.technicaltask2.domain.dto.FileDTO;
import relucky.code.technicaltask2.domain.entity.File;
import relucky.code.technicaltask2.domain.entity.Task;
import relucky.code.technicaltask2.domain.entity.User;
import relucky.code.technicaltask2.domain.mapper.FileMapper;
import relucky.code.technicaltask2.domain.repository.FileRepository;
import relucky.code.technicaltask2.domain.repository.TaskRepository;
import relucky.code.technicaltask2.domain.repository.UserRepository;
import relucky.code.technicaltask2.domain.service.MinioService;
import relucky.code.technicaltask2.domain.service.UserService;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {
    private final MinioClient minioClient;
    private final FileRepository fileRepository;
    private final TaskRepository taskRepository;
    private final FileMapper fileMapper;
    private final UserService userService;

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
    public FileDTO uploadFile(MultipartFile file, Long taskId){
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        User currentUser = userService.getUser();

        if (taskOptional.isEmpty()) {
            throw new TaskNotFoundException("Task with id: " + taskId + " does not exist");
        }

        Task task = taskOptional.get();

        if (!currentUser.getTaskList().contains(task)) {
            throw new UnauthorizedAccessException("User does not have access to task with id: " + taskId);
        }

        try {
            String minioPath = uploadFileToMinio(file);
            File fileEntity = File.builder()
                    .fileName(file.getOriginalFilename())
                    .minioPath(minioPath)
                    .fileType(file.getContentType())
                    .task(task)
                    .build();
            fileRepository.save(fileEntity);
            return fileMapper.toDTO(fileEntity);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }


    @Override
    public FileDTO deleteFile(Long fileId, Long taskId){
        User currentUser = userService.getUser();
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isEmpty()){
            throw new TaskNotFoundException("Task with id: " + taskId + " not found");
        }
        Task task = taskOptional.get();
        if (!currentUser.getTaskList().contains(task)) {
            throw new UnauthorizedAccessException("User does not have access to task with id: " + taskId);
        }
        Optional<File> fileOptional = fileRepository.findById(fileId);
        if (fileOptional.isEmpty()) {
            throw new CFileNotFoundException("File with id " + fileId + " not found");
        }
        File file = fileOptional.get();
        if (!file.getTask().equals(task)) {
            throw new UnauthorizedAccessException("User does not have access to file with id: " + fileId);
        }
        deleteFileFromMinio(file.getMinioPath());
        task.getFileList().remove(file);
        fileRepository.deleteById(fileId);
        return fileMapper.toDTO(file);
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

}
