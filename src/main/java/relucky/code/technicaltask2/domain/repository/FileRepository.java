package relucky.code.technicaltask2.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import relucky.code.technicaltask2.domain.entity.File;

public interface FileRepository extends JpaRepository<File, Long> {
}
