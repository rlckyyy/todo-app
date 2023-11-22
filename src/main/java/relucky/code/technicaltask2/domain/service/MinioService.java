package relucky.code.technicaltask2.domain.service;

import org.springframework.web.multipart.MultipartFile;
import relucky.code.technicaltask2.domain.dto.FileDTO;
import relucky.code.technicaltask2.domain.entity.File;
import relucky.code.technicaltask2.domain.entity.Task;

public interface MinioService {
    String uploadFileToMinio(MultipartFile file);
    File uploadFile(MultipartFile file, Long taskId);

    FileDTO deleteFile(Long fileId, Long taskId);
}
