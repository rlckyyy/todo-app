package relucky.code.technicaltask2.domain.dto;

public record FileDTO(
        Long id,
        String fileName,
        String minioPath,
        String fileType
) {
}
