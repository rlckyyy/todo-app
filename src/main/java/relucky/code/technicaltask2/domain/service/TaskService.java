package relucky.code.technicaltask2.domain.service;

import relucky.code.technicaltask2.domain.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    void createTask(TaskDTO taskDTO);
    void deleteTask(Long id);
    List<TaskDTO> findAllTask();
    TaskDTO findTask(Long id);
}
