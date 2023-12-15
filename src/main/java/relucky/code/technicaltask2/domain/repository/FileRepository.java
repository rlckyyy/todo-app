package relucky.code.technicaltask2.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import relucky.code.technicaltask2.domain.entity.File;

public interface FileRepository extends MongoRepository<File, String> {
}
