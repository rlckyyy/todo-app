package relucky.code.technicaltask2.domain.service;

import relucky.code.technicaltask2.domain.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    TaskDTO createTask(TaskDTO taskDTO);
    TaskDTO deleteTask(String id);
    List<TaskDTO> findAllTask();
    TaskDTO findTask(String id);
}
