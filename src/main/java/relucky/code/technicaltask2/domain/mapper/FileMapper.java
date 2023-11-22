package relucky.code.technicaltask2.domain.mapper;

import org.mapstruct.Mapper;
import relucky.code.technicaltask2.domain.dto.FileDTO;
import relucky.code.technicaltask2.domain.entity.File;

@Mapper
public interface FileMapper {
    FileDTO toDTO(File file);
    File toModel(FileDTO fileDTO);
}
