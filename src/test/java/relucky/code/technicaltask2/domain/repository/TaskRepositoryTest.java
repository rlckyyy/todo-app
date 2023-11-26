package relucky.code.technicaltask2.domain.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import relucky.code.technicaltask2.common.enums.Role;
import relucky.code.technicaltask2.domain.entity.Task;
import relucky.code.technicaltask2.domain.entity.User;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindAllByUser_whenUserHaveTask_returnListTask() {
        // given
        User user = new User(1L, "Yernur","reluckytryhrd@gmail.com",18,"Ernur777", Role.USER, null);
        userRepository.save(user);
        List<Task> taskList = List.of(
                new Task(1L, "Buy products", "We should buy products like milk", user, null),
                new Task(2L, "Buy cat", "We should buy a cat", user, null)
        );
        taskRepository.saveAll(taskList);
        // when
        List<Task> tasks = taskRepository.findAllByUser(user);
        // then
        assertThat(tasks).isNotEmpty().hasSize(2);
    }

    @Test
    void testFindAllByUser_whenUserDoNotHaveTask_returnEmptyList(){
        // given
        User user = new User(1L, "Yernur","reluckytryhrd@gmail.com",18,"Ernur777", Role.USER, null);
        userRepository.save(user);
        // when
        List<Task> tasks = taskRepository.findAllByUser(user);
        // then
        assertThat(tasks).isEmpty();
        assertThat(tasks).hasSize(0);
    }
}