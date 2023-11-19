package relucky.code.technicaltask2.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import relucky.code.technicaltask2.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
