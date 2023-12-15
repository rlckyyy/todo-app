package relucky.code.technicaltask2.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "task")
@Data
@AllArgsConstructor@NoArgsConstructor
@Builder
public class Task {
    @Id
    private String id;
    private String title;
    private String description;
    private User user;
    private List<File> fileList;
}
