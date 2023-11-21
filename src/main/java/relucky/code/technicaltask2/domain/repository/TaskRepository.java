package relucky.code.technicaltask2.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import relucky.code.technicaltask2.domain.entity.Task;
import relucky.code.technicaltask2.domain.entity.User;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByUser(User user);
}