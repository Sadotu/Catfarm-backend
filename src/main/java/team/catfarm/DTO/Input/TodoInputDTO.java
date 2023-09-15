package team.catfarm.DTO.Input;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoInputDTO {
    @Id
    private Long id;
    @Size(max = 255)
    private String description;
    private boolean completed;
    private Long task_id;

    public boolean isCompleted() {
        return completed;
    }
}
