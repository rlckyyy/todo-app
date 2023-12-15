package relucky.code.technicaltask2.domain.dto;

public record FileDTO(
        String id,
        String fileName,
        String minioPath,
        String fileType
) {
}
