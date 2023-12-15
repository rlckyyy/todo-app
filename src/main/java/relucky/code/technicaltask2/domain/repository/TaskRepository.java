package relucky.code.technicaltask2.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import relucky.code.technicaltask2.domain.entity.Task;
import relucky.code.technicaltask2.domain.entity.User;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findAllByUser(User user);
}
