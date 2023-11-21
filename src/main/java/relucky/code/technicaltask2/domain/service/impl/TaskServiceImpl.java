package relucky.code.technicaltask2.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import relucky.code.technicaltask2.common.exception.TaskNotFoundException;
import relucky.code.technicaltask2.common.exception.UnauthorizedTaskAccessException;
import relucky.code.technicaltask2.common.exception.UserNotFoundException;
import relucky.code.technicaltask2.domain.dto.TaskDTO;
import relucky.code.technicaltask2.domain.entity.Task;
import relucky.code.technicaltask2.domain.entity.User;
import relucky.code.technicaltask2.domain.mapper.TaskMapper;
import relucky.code.technicaltask2.domain.repository.FileRepository;
import relucky.code.technicaltask2.domain.repository.TaskRepository;
import relucky.code.technicaltask2.domain.repository.UserRepository;
import relucky.code.technicaltask2.domain.service.TaskService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = taskMapper.toModel(taskDTO);
        task.setUser(getUser());
        taskRepository.save(task);
        return taskDTO;
    }

    @Override
    public TaskDTO deleteTask(Long id) {
        User currentUser = getUser();
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()){
            Task task = taskOptional.get();
            if (task.getUser().equals(currentUser)){
                taskRepository.deleteById(id);
                return taskMapper.toDto(task);
            } else {
                throw new UnauthorizedTaskAccessException("User does not have access to task with id: " + id);
            }
        } else {
            throw new TaskNotFoundException("Task with id: " + id + " does not exist");
        }
    }


    @Override
    public List<TaskDTO> findAllTask() {
        return taskRepository.findAllByUser(getUser()).stream().map(taskMapper::toDto).toList();
    }

    @Override
    public TaskDTO findTask(Long id) {
        User currentUser = getUser();
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent() && taskOptional.get().getUser().equals(currentUser)){
            return taskMapper.toDto(taskOptional.get());
        } else {
            throw new TaskNotFoundException("Task not found or task is not your");
        }
    }

    private User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
