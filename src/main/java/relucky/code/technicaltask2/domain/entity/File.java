package relucky.code.technicaltask2.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor@NoArgsConstructor
@Getter@Setter
@Builder
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String minioPath;
    private String fileType;
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
}
