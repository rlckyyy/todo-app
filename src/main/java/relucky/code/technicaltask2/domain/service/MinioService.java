package relucky.code.technicaltask2.domain.service;

import org.springframework.web.multipart.MultipartFile;
import relucky.code.technicaltask2.domain.dto.FileDTO;

public interface MinioService {
    String uploadFileToMinio(MultipartFile file);
    FileDTO uploadFile(MultipartFile file, String taskId);
    FileDTO deleteFile(String fileId, String taskId);
}
