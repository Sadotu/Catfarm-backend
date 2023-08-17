package team.catfarm.DTO.Output;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoOutputDTO {
    @Id
    private Long id;
    private String description;
    private boolean completed;
    private Long task_id;
}
