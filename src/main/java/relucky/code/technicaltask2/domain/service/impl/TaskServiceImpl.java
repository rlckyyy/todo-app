package relucky.code.technicaltask2.domain.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import relucky.code.technicaltask2.common.exception.TaskNotFoundException;
import relucky.code.technicaltask2.common.exception.UnauthorizedAccessException;
import relucky.code.technicaltask2.domain.dto.TaskDTO;
import relucky.code.technicaltask2.domain.entity.Task;
import relucky.code.technicaltask2.domain.entity.User;
import relucky.code.technicaltask2.domain.mapper.TaskMapper;
import relucky.code.technicaltask2.domain.repository.TaskRepository;
import relucky.code.technicaltask2.domain.service.TaskService;
import relucky.code.technicaltask2.domain.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
    private final UserService userService;
    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = taskMapper.toModel(taskDTO);
        task.setUser(userService.getUser());
        taskRepository.save(task);
        return taskDTO;
    }

    @Transactional
    @Override
    public TaskDTO deleteTask(String id) {
        User currentUser = userService.getUser();
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()){
            Task task = taskOptional.get();
            if (task.getUser().equals(currentUser)){
                taskRepository.deleteById(id);
                return taskMapper.toDto(task);
            } else {
                throw new UnauthorizedAccessException("User does not have access to task with id: " + id);
            }
        } else {
            throw new TaskNotFoundException("Task with id: " + id + " does not exist");
        }
    }


    @Override
    public List<TaskDTO> findAllTask() {
        return taskRepository.findAllByUser(userService.getUser()).stream().map(taskMapper::toDto).toList();
    }

    @Override
    public TaskDTO findTask(String id) {
        User currentUser = userService.getUser();
        Optional<Task> taskOptional = taskRepository.findById(id);
        System.out.println(taskOptional.get().getUser() + " " + currentUser);
        if (taskOptional.isPresent() && taskOptional.get().getUser().equals(currentUser)){
            return taskMapper.toDto(taskOptional.get());
        } else {
            throw new TaskNotFoundException("Task not found or task is not your");
        }
    }

}
