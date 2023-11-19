package relucky.code.technicaltask2.domain.mapper;

import org.mapstruct.Mapper;
import relucky.code.technicaltask2.domain.dto.TaskDTO;
import relucky.code.technicaltask2.domain.entity.Task;

@Mapper
public interface TaskMapper {
    Task toModel(TaskDTO taskDTO);
    TaskDTO toDTO(Task task);
}
