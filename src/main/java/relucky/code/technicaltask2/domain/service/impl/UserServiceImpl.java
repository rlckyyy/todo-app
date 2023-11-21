package relucky.code.technicaltask2.domain.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import relucky.code.technicaltask2.domain.dto.TaskDTO;
import relucky.code.technicaltask2.domain.entity.Task;
import relucky.code.technicaltask2.domain.entity.User;
import relucky.code.technicaltask2.domain.mapper.TaskMapper;
import relucky.code.technicaltask2.domain.repository.TaskRepository;
import relucky.code.technicaltask2.domain.repository.UserRepository;
import relucky.code.technicaltask2.domain.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;


    @Override
    public void createTask(TaskDTO taskDTO) {
        Task task = taskMapper.toModel(taskDTO);
        task.setUser(getUser());
        taskRepository.save(task);
    }
    @Override
    public void deleteTask(Long id) {
        User currentUser = getUser();
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent() && taskOptional.get().getUser().equals(currentUser)){
            taskRepository.deleteById(id);
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
            throw new IllegalStateException("Task not found or task is not your");
        }
    }
    private User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
