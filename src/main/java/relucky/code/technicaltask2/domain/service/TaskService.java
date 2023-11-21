package relucky.code.technicaltask2.domain.service;

import relucky.code.technicaltask2.domain.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    TaskDTO createTask(TaskDTO taskDTO);
    TaskDTO deleteTask(Long id);
    List<TaskDTO> findAllTask();
    TaskDTO findTask(Long id);
}
