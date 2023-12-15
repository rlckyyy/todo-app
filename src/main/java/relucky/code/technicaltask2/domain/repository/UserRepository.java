package relucky.code.technicaltask2.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import relucky.code.technicaltask2.domain.entity.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}
