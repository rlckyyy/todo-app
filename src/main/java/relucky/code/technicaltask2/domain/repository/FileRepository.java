package relucky.code.technicaltask2.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import relucky.code.technicaltask2.domain.entity.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    void deleteAllByTaskId(Long task_id);
}
