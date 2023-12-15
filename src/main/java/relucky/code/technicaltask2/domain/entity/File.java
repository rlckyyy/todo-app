package relucky.code.technicaltask2.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "task")
@AllArgsConstructor@NoArgsConstructor
@Getter@Setter
@Builder
public class File {
    @Id
    private String id;
    private String fileName;
    private String minioPath;
    private String fileType;
    @JsonIgnore
    private Task task;
}
